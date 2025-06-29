package at.fhburgenland.service;

import at.fhburgenland.model.Fahrer;
import at.fhburgenland.model.Fahrzeug;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Fahrer-Entitäten
 * Beinhaltet CRUD-Methoden und Überprüfung von Verknüpfungen
 */
public class FahrerService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Speichert einen neuen Fahrer in der Datenbank
     *
     * @param fahrer Fahrer, der gespeichert wird
     */
    public static void fahrerHinzufuegen(Fahrer fahrer) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            System.out.println("Neuer Fahrer wurde angelegt: " + fahrer);
            em.persist(fahrer);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern des Fahrers: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Gibt alle Fahrer aus der Datenbank zurück
     *
     * @return Liste aller Fahrer
     */
    public static List<Fahrer> alleFahrerAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT f FROM Fahrer f";
        TypedQuery<Fahrer> tq = em.createQuery(query, Fahrer.class);

        List<Fahrer> fahrerListe = null;

        try {
            fahrerListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return fahrerListe;
    }

    /**
     * Sucht einen Fahrer anhand der ID und gibt ihn zurück und seine Informationen in der Konsole aus
     *
     * @param fahrerId ID des Fahrers
     * @return Gefundener Fahrer oder null
     */
    public static Fahrer fahrerAnzeigenNachId(int fahrerId) {
        EntityManager em = EMF.createEntityManager();
        Fahrer fahrer = null;

        try {
            fahrer = em.find(Fahrer.class, fahrerId);
            if (fahrer != null) {
                System.out.println("Fahrer gefunden:");
                System.out.println("ID: " + fahrer.getFahrerId());
                System.out.println("Vorname: " + fahrer.getVorname());
                System.out.println("Nachname: " + fahrer.getNachname());
                System.out.println("Nationalität: " + fahrer.getNationalitaet());
                System.out.println("Fahrzeug:" + fahrer.getFahrzeug());
            } else {
                System.err.println("Kein Fahrer mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return fahrer;
    }

    /**
     * Aktualisiert einen Fahrer in der Datenbank
     *
     * @param fahrer Fahrer mit aktualisierten Werten
     */
    public static void fahrerUpdaten(Fahrer fahrer) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(fahrer);
            et.commit();
            System.out.println("Fahrer erfolgreich aktualisiert: " + fahrer);

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Löscht einen Fahrer anhand seiner ID, wenn keine Verknüpfungen bestehen
     *
     * @param fahrerId ID des Fahrers, der gelöscht wird
     */
    public static void fahrerLoeschen(int fahrerId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Fahrer fahrer = em.find(Fahrer.class, fahrerId);
            if (fahrer == null) {
                System.err.println("Fahrer nicht gefunden.");
                et.rollback();
                return;
            }
            String begruendung = pruefeVerknuepfungMitFahrer(fahrer);
            if (begruendung != null) {
                System.err.println(begruendung);
                et.rollback();
                return;
            }

            em.remove(fahrer);
            et.commit();

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Fahrers." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }

    /**
     * Prüft, ob ein Fahrzeug bereits einem anderen Fahrer zugeordnet ist
     *
     * @param fahrzeug             das zu überprüfende Fahrzeug
     * @param ausgenommeneFahrerId ID des Fahrers, der ausgenommen werden soll
     * @return true, wenn Fahrzeug vergeben ist, sonst false
     */
    public static boolean fahrzeugBereitsVergeben(Fahrzeug fahrzeug, int ausgenommeneFahrerId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            String jpql = "SELECT COUNT(f) FROM Fahrer f WHERE f.fahrzeug = :fahrzeug AND f.fahrerId != :id";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("fahrzeug", fahrzeug)
                    .setParameter("id", ausgenommeneFahrerId)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return false;
    }

    /**
     * Prüft, ob ein Fahrer mit anderen Entitäten verknüpft ist
     * Wird verwendet, um das Löschen abzusichern
     *
     * @param fahrer Fahrer, der überprüft wird
     * @return Rückgabe einer Begründung als String oder null, wenn keine Verknüpfung besteht
     */
    public static String pruefeVerknuepfungMitFahrer(Fahrer fahrer) {
        if (fahrer == null) {
            return "Fahrer ist null. Prüfung nicht möglich.";
        }
        try {
            boolean hatNationalitaet = fahrer.getNationalitaet() != null;
            boolean faehrtFahrzeug = fahrer.getFahrzeug() != null;
            if (hatNationalitaet || faehrtFahrzeug) {
                StringBuilder grund = new StringBuilder("Fahrer kann nicht gelöscht werden, da er ");
                if (hatNationalitaet && faehrtFahrzeug) {
                    grund.append("einer Nationalität und einem Fahrzeug");
                } else if (hatNationalitaet) {
                    grund.append("einer Nationalität ");
                } else {
                    grund.append("einem Fahrzeug ");
                }
                grund.append("zugeordnet ist.");
                return grund.toString();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

