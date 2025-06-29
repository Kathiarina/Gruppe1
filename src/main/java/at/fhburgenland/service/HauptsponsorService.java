package at.fhburgenland.service;

import at.fhburgenland.model.Hauptsponsor;
import jakarta.persistence.*;

import java.util.List;
/**
 * Service-Klasse zur Verwaltung von Hauptsponsor-Entitäten
 * Beinhaltet CRUD-Methoden und Überprüfung von Verknüpfungen
 */
public class HauptsponsorService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Speichert einen neuen Hauptsponsor in der Datenbank
     * @param hauptsponsor Hauptsponsor, der gespeichert wird
     */
    public static void hauptsponsorHinzufuegen(Hauptsponsor hauptsponsor) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            System.out.println("Neuer Hauptsponsor wurde angelegt: " + hauptsponsor);
            em.persist(hauptsponsor);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern des Hauptsponsors: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Gibt alle Hauptsponsoren aus der Datenbank zurück
     * @return Liste aller Hauptsponsoren
     */
    public static List<Hauptsponsor> alleHauptsponsorenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT hs FROM Hauptsponsor hs";
        TypedQuery<Hauptsponsor> tq = em.createQuery(query, Hauptsponsor.class);

        List<Hauptsponsor> hauptsponsorenListe = null;

        try {
            hauptsponsorenListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return hauptsponsorenListe;
    }

    /**
     * Sucht einen Hauptsponsor anhand der ID und gibt ihn zurück und seine Informationen in der Konsole aus
     * @param hauptsponsorId ID des Hauptsponsors
     * @return Gefundener Hauptsponsor oder null
     */
    public static Hauptsponsor hauptsponsorAnzeigenNachId(int hauptsponsorId) {
        EntityManager em = EMF.createEntityManager();
        Hauptsponsor hauptsponsor = null;

        try {
            hauptsponsor = em.find(Hauptsponsor.class, hauptsponsorId);
            if (hauptsponsor != null) {
                System.out.println("Hauptsponsor gefunden:");
                System.out.println("ID: " + hauptsponsor.getHauptsponsorId());
                System.out.println("Name: " + hauptsponsor.getHauptsponsorName());
                System.out.println("Jährliche Sponsorsumme: " + hauptsponsor.getJaehrlicheSponsorsumme());
            } else {
                System.err.println("Kein Hauptsponsor mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return hauptsponsor;
    }

    /**
     * Aktualisiert einen Hauptsponsor in der Datenbank
     * @param hauptsponsor Hauptsponsor mit aktualisierten Werten
     */
    public static void hauptsponsorUpdaten(Hauptsponsor hauptsponsor) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(hauptsponsor);
            et.commit();
            System.out.println("Hauptsponsor erfolgreich aktualisiert: " + hauptsponsor);

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
     * Löscht einen Hauptsponsor anhand der ID, wenn keine Verknüpfungen bestehen
     * @param hauptsponsorId ID des Hauptsponsors, der gelöscht wird
     */
    public static void hauptsponsorLoeschen(int hauptsponsorId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Hauptsponsor hauptsponsor = em.find(Hauptsponsor.class, hauptsponsorId);
            if (hauptsponsor == null) {
                System.err.println("Hauptsponsor nicht gefunden.");
                et.rollback();
                return;
            }
            if (hauptsponsor.getTeam() != null) {
                System.err.println("Hauptsponsor kann nicht gelöscht werden, da er einem Team zugeordnet ist.");
                et.rollback();
                return;
            }
            em.remove(hauptsponsor);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Hauptsponsors." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }
}
