package at.fhburgenland.service;

import at.fhburgenland.model.Rennstrecke;
import jakarta.persistence.*;

import java.util.List;

public class RennstreckeService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

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
        } finally {
            em.close();
        }
    }

    public static List<Rennstrecke> alleRennstreckenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT rs FROM Rennstrecke rs";
        TypedQuery<Rennstrecke> tq = em.createQuery(query, Rennstrecke.class);

        List<Rennstrecke> rennstreckenListe = null;

        try {
            rennstreckenListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return rennstreckenListe;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
        return rennstrecke;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void rennstreckeLoeschen(int rennstreckenId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Rennstrecke rennstrecke = em.find(Rennstrecke.class, rennstreckenId);
            if (rennstrecke != null) {
                if(!rennstrecke.getRennen().isEmpty()){
                    System.err.println("Rennstrecke kann nicht gelöscht werden, da sie einem Rennen zugeordnet ist.");
                    et.rollback();
                    return;
                }
                em.remove(rennstrecke);
                et.commit();
            } else {
                System.err.println("Rennstrecke nicht gefunden.");
            }
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.err.println("Fehler beim Löschen der Rennstrecke.");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }
}
