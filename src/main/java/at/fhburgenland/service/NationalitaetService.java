package at.fhburgenland.service;

import at.fhburgenland.model.Nationalitaet;
import jakarta.persistence.*;

import java.util.List;


public class NationalitaetService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

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
        } finally {
            em.close();
        }
    }

    public static List<Nationalitaet> alleNationalitaetenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT n FROM Nationalitaet n";
        TypedQuery<Nationalitaet> tq = em.createQuery(query, Nationalitaet.class);

        List<Nationalitaet> nationalitaetenListe = null;

        try {
            nationalitaetenListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return nationalitaetenListe;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
        return nationalitaet;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void nationalitaetLoeschen(int nationalitaetsId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Nationalitaet nationalitaet = em.find(Nationalitaet.class, nationalitaetsId);
            if (nationalitaet != null) {
                em.remove(nationalitaet);
                et.commit();
            } else {
                System.err.println("Nationalität nicht gefunden.");
            }
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen der Nationalitaet.");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }
}
