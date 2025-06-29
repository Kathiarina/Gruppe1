package at.fhburgenland.service;

import at.fhburgenland.model.Rennstrecke;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Rennstrecken-Entitäten
 * Beinhaltet CRUD-Methoden und Überprüfung von Verknüpfungen
 */
public class RennstreckeService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Speichert eine neue Rennstrecke in der Datenbank
     *
     * @param rennstrecke Rennstrecke, die gespeichert wird
     */
    public static void rennstreckeHinzufuegen(Rennstrecke rennstrecke) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            System.out.println("Neue Rennstrecke wurde angelegt: " + rennstrecke);
            em.persist(rennstrecke);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern der Rennstrecke: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Gibt alle Rennstrecken aus der Datenbank zurück
     *
     * @return Liste aller Rennstrecken
     */
    public static List<Rennstrecke> alleRennstreckenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT rs FROM Rennstrecke rs";
        TypedQuery<Rennstrecke> tq = em.createQuery(query, Rennstrecke.class);

        List<Rennstrecke> rennstreckenListe = null;

        try {
            rennstreckenListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return rennstreckenListe;
    }

    /**
     * Sucht eine Rennstrecke anhand der ID und gibt sie zurück und ihre Informationen in der Konsole aus
     *
     * @param rennstreckenId ID der Rennstrecke
     * @return Gefundene Rennstrecke oder null
     */
    public static Rennstrecke rennstreckeAnzeigenNachId(int rennstreckenId) {
        EntityManager em = EMF.createEntityManager();
        Rennstrecke rennstrecke = null;

        try {
            rennstrecke = em.find(Rennstrecke.class, rennstreckenId);
            if (rennstrecke != null) {
                System.out.println("Rennstrecke gefunden:");
                System.out.println("ID: " + rennstrecke.getRennstreckenId());
                System.out.println("Ort: " + rennstrecke.getOrt());
                System.out.println("Bundesland: " + rennstrecke.getBundesland());
            } else {
                System.err.println("Keine Rennstrecke mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return rennstrecke;
    }

    /**
     * Aktualisiert eine Rennstrecke in der Datenbank
     *
     * @param rennstrecke Rennstrecke mit aktualisierten Werten
     */
    public static void rennstreckeUpdaten(Rennstrecke rennstrecke) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(rennstrecke);
            et.commit();
            System.out.println("Rennstrecke erfolgreich aktualisiert: " + rennstrecke);

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
     * Löscht eine Rennstrecke anhand der ID, wenn keine Verknüpfungen bestehen
     *
     * @param rennstreckenId ID der Rennstrecke, die gelöscht wird
     */
    public static void rennstreckeLoeschen(int rennstreckenId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Rennstrecke rennstrecke = em.find(Rennstrecke.class, rennstreckenId);
            if (rennstrecke == null) {
                System.err.println("Rennstrecke nicht gefunden.");
                et.rollback();
                return;
            }
            if (rennstrecke.getRennen() != null && !rennstrecke.getRennen().isEmpty()) {
                System.err.println("Rennstrecke kann nicht gelöscht werden, da sie einem Rennen zugeordnet ist.");
                et.rollback();
                return;
            }
            em.remove(rennstrecke);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.err.println("Fehler beim Löschen der Rennstrecke." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }
}
