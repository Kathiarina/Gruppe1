package at.fhburgenland.service;

import at.fhburgenland.model.Fahrzeugtyp;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Fahrzeugtyp-Entitäten
 * Beinhaltet CRUD-Methoden und Überprüfung von Verknüpfungen
 */
public class FahrzeugtypService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Speichert einen neuen Fahrzeugtyp in der Datenbank
     * @param fahrzeugtyp Fahrzeugtyp, der gespeichert wird
     */
    public static void fahrzeugtypHinzufuegen(Fahrzeugtyp fahrzeugtyp) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            System.out.println("Neuer Fahrzeugtyp wurde angelegt: " + fahrzeugtyp);
            em.persist(fahrzeugtyp);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern des Fahrzeugtyps: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Gibt alle Fahrzeugtypen aus der Datenbank zurück
     * @return Liste aller Fahrzeugtypen
     */
    public static List<Fahrzeugtyp> alleFahrzeugtypenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT ft FROM Fahrzeugtyp ft";
        TypedQuery<Fahrzeugtyp> tq = em.createQuery(query, Fahrzeugtyp.class);

        List<Fahrzeugtyp> fahrzeugtypenListe = null;

        try {
            fahrzeugtypenListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return fahrzeugtypenListe;
    }

    /**
     * Sucht einen Fahrzeugtyp anhand der ID und gibt ihn zurück und seine Informationen in der Konsole aus
     * @param fahrzeugtypId ID des Fahrzeugtyps
     * @return Gefundener Fahrzeugtyp oder null
     */
    public static Fahrzeugtyp fahrzeugtypAnzeigenNachId(int fahrzeugtypId) {
        EntityManager em = EMF.createEntityManager();
        Fahrzeugtyp fahrzeugtyp = null;

        try {
            fahrzeugtyp = em.find(Fahrzeugtyp.class, fahrzeugtypId);
            if (fahrzeugtyp != null) {
                System.out.println("Fahrzeugtyp gefunden:");
                System.out.println("ID: " + fahrzeugtyp.getFahrzeugtypId());
                System.out.println("Modell: " + fahrzeugtyp.getModell());
                System.out.println("Motor: " + fahrzeugtyp.getMotor());
                System.out.println("Gewicht: " + fahrzeugtyp.getGewichtKg() + "kg");
            } else {
                System.err.println("Kein Fahrzeugtyp mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return fahrzeugtyp;
    }

    /**
     * Aktualisiert einen Fahrzeugtyp in der Datenbank
     * @param fahrzeugtyp Fahrzeugtyp mit aktualisierten Werten
     */
    public static void fahrzeugtypUpdaten(Fahrzeugtyp fahrzeugtyp) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(fahrzeugtyp);
            et.commit();
            System.out.println("Fahrzeugtyp erfolgreich aktualisiert: " + fahrzeugtyp);

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
     * Löscht einen Fahrzeugtyp anhand der ID, wenn keine Verknüpfungen bestehen
     * @param fahrzeugtypId ID des Fahrzeugtyps, der gelöscht wird
     */
    public static void fahrzeugtypLoeschen(int fahrzeugtypId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Fahrzeugtyp fahrzeugtyp = em.find(Fahrzeugtyp.class, fahrzeugtypId);
            if (fahrzeugtyp == null) {
                System.err.println("Fahrzeugtyp nicht gefunden.");
                et.rollback();
                return;
            }
            if (fahrzeugtyp.getFahrzeug() != null && !fahrzeugtyp.getFahrzeug().isEmpty()) {
                System.err.println("Fahrzeugtyp kann nicht gelöscht werden, da er einem Fahrzeug zugeordnet ist.");
                et.rollback();
                return;
            }
            em.remove(fahrzeugtyp);
            et.commit();

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Fahrzeugtyps." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }
}
