package at.fhburgenland.service;

import at.fhburgenland.model.*;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Fahrzeug-Entitäten
 * Beinhaltet CRUD-Methoden und Überprüfung von Verknüpfungen
 */
public class FahrzeugService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Speichert ein neues Fahrzeug in der Datenbank
     *
     * @param fahrzeug Fahrzeug, das gespeichert wird
     */
    public static void fahrzeugHinzufuegen(Fahrzeug fahrzeug) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            System.out.println("Neues Fahrzeug wurde angelegt: " + fahrzeug);
            em.persist(fahrzeug);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern des Fahrzeugs: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Gibt alle Fahrzeuge aus der Datenbank zurück
     *
     * @return Liste aller Fahrzeuge
     */
    public static List<Fahrzeug> alleFahrzeugeAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT fz FROM Fahrzeug fz";
        TypedQuery<Fahrzeug> tq = em.createQuery(query, Fahrzeug.class);

        List<Fahrzeug> fahrzeugListe = null;

        try {
            fahrzeugListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return fahrzeugListe;
    }

    /**
     * Sucht ein Fahrzeug anhand der ID und gibt es zurück und seine Informationen in der Konsole aus
     *
     * @param fahrzeugId ID des Fahrzeugs
     * @return Gefundenes Fahrzeug oder null
     */
    public static Fahrzeug fahrzeugAnzeigenNachId(int fahrzeugId) {
        EntityManager em = EMF.createEntityManager();
        Fahrzeug fahrzeug = null;

        try {
            fahrzeug = em.find(Fahrzeug.class, fahrzeugId);
            if (fahrzeug != null) {
                System.out.println("Fahrzeug gefunden:");
                System.out.println("ID: " + fahrzeug.getFahrzeugId());
                System.out.println("Fahrzeugtyp: " + fahrzeug.getFahrzeugtyp());
                System.out.println("Team: " + fahrzeug.getTeam());
            } else {
                System.err.println("Kein Fahrzeug mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return fahrzeug;
    }

    /**
     * Aktualisiert ein Fahrzeug in der Datenbank
     *
     * @param fahrzeug Fahrzeug mit aktualisierten Werten
     */
    public static void fahrzeugUpdaten(Fahrzeug fahrzeug) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(fahrzeug);
            et.commit();
            System.out.println("Fahrzeug erfolgreich aktualisiert: " + fahrzeug);

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
     * Löscht ein Fahrzeug anhand der ID, wenn keine Verknüpfungen bestehen
     *
     * @param fahrzeugId ID des Fahrzeugs, das gelöscht wird
     */
    public static void fahrzeugLoeschen(int fahrzeugId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Fahrzeug fahrzeug = em.find(Fahrzeug.class, fahrzeugId);
            if (fahrzeug == null) {
                System.err.println("Fahrzeug nicht gefunden.");
                et.rollback();
                return;
            }
            String begruendung = pruefeVerknuepfungMitFahrzeug(fahrzeug);
            if (begruendung != null) {
                System.err.println(begruendung);
                et.rollback();
                return;
            }

            em.remove(fahrzeug);
            et.commit();

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Fahrzeugs." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }

    /**
     * Prüft, ob ein Fahrzeug mit anderen Entitäten verknüpft ist
     * Wird verwendet, um das Löschen abzusichern
     *
     * @param fahrzeug Fahrzeug, das überprüft wird
     * @return Rückgabe einer Begründung als String oder null, wenn keine Verknüpfung besteht
     */
    public static String pruefeVerknuepfungMitFahrzeug(Fahrzeug fahrzeug) {
        if (fahrzeug == null) {
            return "Fahrzeug ist null. Prüfung nicht möglich.";
        }
        try {
            boolean gehoertZuTeam = fahrzeug.getTeam() != null;
            boolean istFahrzeugtyp = fahrzeug.getFahrzeugtyp() != null;
            boolean hatFahrer = fahrzeug.getFahrer() != null;

            if (gehoertZuTeam || istFahrzeugtyp || hatFahrer) {
                StringBuilder grund = new StringBuilder("Fahrzeug kann nicht gelöscht werden, da es ");
                boolean first = true;
                if (gehoertZuTeam) {
                    grund.append("einem Team");
                    first = false;
                }
                if (istFahrzeugtyp) {
                    if (!first) grund.append(" & ");
                    grund.append("einem Fahrzeugtyp");
                    first = false;
                }
                if (hatFahrer) {
                    if (!first) grund.append(" & ");
                    grund.append("einem Fahrer");
                }
                grund.append(" zugeordnet ist.");
                return grund.toString();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
