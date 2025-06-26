package at.fhburgenland.service;

import at.fhburgenland.model.Fahrer;
import at.fhburgenland.model.Fahrzeug;
import jakarta.persistence.*;

import java.util.List;

public class FahrerService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

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
        } finally {
            em.close();
        }
    }

    public static List<Fahrer> alleFahrerAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT f FROM Fahrer f";
        TypedQuery<Fahrer> tq = em.createQuery(query, Fahrer.class);

        List<Fahrer> fahrerListe = null;

        try {
            fahrerListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return fahrerListe;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
        return fahrer;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void fahrerLoeschen(int fahrerId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Fahrer fahrer = em.find(Fahrer.class, fahrerId);
            if (fahrer != null) {
                if (fahrer.getNationalitaet() != null || fahrer.getFahrzeug() != null) {
                    System.err.println("Fahrer kann nicht gelöscht werden, da er einer Nationalität oder einem Fahrzeug zugeordnet ist.");
                    et.rollback();
                    return;
                }

                em.remove(fahrer);
                et.commit();
            } else {
                System.err.println("Fahrer nicht gefunden.");
            }
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Fahrers.");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }
}
