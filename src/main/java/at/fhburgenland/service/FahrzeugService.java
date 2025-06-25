package at.fhburgenland.service;

import at.fhburgenland.model.*;
import jakarta.persistence.*;

import java.util.List;

public class FahrzeugService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");


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
        } finally {
            em.close();
        }
    }

    public static List<Fahrzeug> alleFahrzeugeAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT fz FROM Fahrzeug fz";
        TypedQuery<Fahrzeug> tq = em.createQuery(query, Fahrzeug.class);

        List<Fahrzeug> fahrzeugListe = null;

        try {
            fahrzeugListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return fahrzeugListe;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
        return fahrzeug;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    public static void fahrzeugLoeschen(int fahrzeugId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Fahrzeug fahrzeug = em.find(Fahrzeug.class, fahrzeugId);
            if (fahrzeug != null) {
                em.remove(fahrzeug);
                et.commit();
            } else {
                System.err.println("Fahrzeug nicht gefunden.");
            }
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Fahrzeugs.");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }
}
