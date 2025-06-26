package at.fhburgenland.service;

import at.fhburgenland.model.Fahrzeugtyp;
import jakarta.persistence.*;

import java.util.List;

public class FahrzeugtypService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

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
        } finally {
            em.close();
        }
    }

    public static List<Fahrzeugtyp> alleFahrzeugtypenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT ft FROM Fahrzeugtyp ft";
        TypedQuery<Fahrzeugtyp> tq = em.createQuery(query, Fahrzeugtyp.class);

        List<Fahrzeugtyp> fahrzeugtypenListe = null;

        try {
            fahrzeugtypenListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return fahrzeugtypenListe;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
        return fahrzeugtyp;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

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
                System.out.println("Fehler beim Löschen des Fahrzeugtyps.");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }
}
