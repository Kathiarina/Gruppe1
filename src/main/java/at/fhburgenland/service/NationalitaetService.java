package at.fhburgenland.service;

import at.fhburgenland.model.Nationalitaet;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Nationalität-Entitäten
 * Beinhaltet CRUD-Methoden und Überprüfung von Verknüpfungen
 */
public class NationalitaetService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Speichert eine neue Nationalität in der Datenbank
     *
     * @param nationalitaet Nationalität, die gespeichert wird
     */
    public static void nationalitaetHinzufuegen(Nationalitaet nationalitaet) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            System.out.println("Neue Nationalität wurde angelegt: " + nationalitaet);
            em.persist(nationalitaet);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern der Nationalität: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Gibt alle Nationalitäten aus der Datenbank zurück
     *
     * @return Liste aller Nationalitäten
     */
    public static List<Nationalitaet> alleNationalitaetenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT n FROM Nationalitaet n";
        TypedQuery<Nationalitaet> tq = em.createQuery(query, Nationalitaet.class);

        List<Nationalitaet> nationalitaetenListe = null;

        try {
            nationalitaetenListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return nationalitaetenListe;
    }

    /**
     * Sucht eine Nationalität anhand der ID und gibt sie zurück und ihre Informationen in der Konsole aus
     *
     * @param nationalitaetId ID der Nationalität
     * @return Gefundene Nationalität oder null
     */
    public static Nationalitaet nationalitaetAnzeigenNachId(int nationalitaetId) {
        EntityManager em = EMF.createEntityManager();
        Nationalitaet nationalitaet = null;

        try {
            nationalitaet = em.find(Nationalitaet.class, nationalitaetId);
            if (nationalitaet != null) {
                System.out.println("Nationalität gefunden:");
                System.out.println("ID: " + nationalitaet.getNationalitaetsId());
                System.out.println("Nationalität: " + nationalitaet.getNationalitaetsBeschreibung());
            } else {
                System.err.println("Keine Nationalität mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return nationalitaet;
    }

    /**
     * Aktualisiert eine Nationalität in der Datenbank
     *
     * @param nationalitaet Nationalität mit aktualisierten Werten
     */
    public static void nationalitaetUpdaten(Nationalitaet nationalitaet) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(nationalitaet);
            et.commit();
            System.out.println("Nationalität erfolgreich aktualisiert: " + nationalitaet);

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
     * Löscht eine Nationalität anhand der ID, wenn keine Verknüpfungen bestehen
     *
     * @param nationalitaetsId ID der Nationalität, die gelöscht wird
     */
    public static void nationalitaetLoeschen(int nationalitaetsId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Nationalitaet nationalitaet = em.find(Nationalitaet.class, nationalitaetsId);
            if (nationalitaet == null) {
                System.err.println("Nationalität nicht gefunden.");
                et.rollback();
                return;
            }
            String begruendung = pruefeVerknuepfungMitNationalitaet(nationalitaet);
            if (begruendung != null) {
                System.err.println(begruendung);
                et.rollback();
                return;
            }
            em.remove(nationalitaet);
            et.commit();

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen der Nationalität." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }

    /**
     * Prüft, ob eine Nationalität mit anderen Entitäten verknüpft ist
     * Wird verwendet, um das Löschen abzusichern
     *
     * @param nationalitaet Nationalität, die überprüft wird
     * @return Rückgabe einer Begründung als String oder null, wenn keine Verknüpfung besteht
     */
    public static String pruefeVerknuepfungMitNationalitaet(Nationalitaet nationalitaet) {
        if (nationalitaet == null) {
            return "Nationalität ist null. Prüfung nicht möglich.";
        }

        try {
            boolean hatFahrer = nationalitaet.getFahrer() != null && !nationalitaet.getFahrer().isEmpty();
            boolean hatTeam = nationalitaet.getTeam() != null && !nationalitaet.getTeam().isEmpty();

            if (hatFahrer || hatTeam) {
                StringBuilder grund = new StringBuilder("Nationalität kann nicht gelöscht werden, da sie ");
                if (hatFahrer && hatTeam) {
                    grund.append("einem Fahrer und einem Team ");
                } else if (hatFahrer) {
                    grund.append("einem Fahrer ");
                } else {
                    grund.append("einem Team ");
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
