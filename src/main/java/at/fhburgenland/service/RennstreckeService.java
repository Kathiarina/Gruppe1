package at.fhburgenland.service;

import at.fhburgenland.model.Rennstrecke;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class RennstreckeService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    private static Scanner scanner = new Scanner(System.in);

    public static void rennstreckeHinzufuegen(String ort, String bundesland) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Rennstrecke rennstrecke = new Rennstrecke(ort, bundesland);
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

    public static void alleRennstreckenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT rs FROM Rennstrecke rs";
        TypedQuery<Rennstrecke> tq = em.createQuery(query, Rennstrecke.class);

        List<Rennstrecke> rennstreckenListe = null;

        try {
            rennstreckenListe = tq.getResultList();
            for (Rennstrecke rennstrecke : rennstreckenListe) {
                System.out.println("Rennstrecke Nr: " + rennstrecke.getRennstreckenId() + ", Ort " + rennstrecke.getOrt() + ", Bundesland " + rennstrecke.getBundesland());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static Rennstrecke rennstreckeAuswaehlen() {
        alleRennstreckenAnzeigen();
        System.out.println("Bitte gib die ID der gewünschten Rennstrecke ein:");
        int rennstreckenId = scanner.nextInt();
        scanner.nextLine();
        EntityManager em = EMF.createEntityManager();
        Rennstrecke ausgewaehlteRennstrecke = null;
        try {
            ausgewaehlteRennstrecke = em.find(Rennstrecke.class, rennstreckenId);
            if (ausgewaehlteRennstrecke == null) {
                System.err.println("Keine Rennstrecke mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ausgewaehlteRennstrecke;
    }

/*
    public static void updateRennstrecke(int rennstreckeId, String ort, String bundesland, Rennen rennen) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Rennstrecke rennstrecke = null;

        try {
            et = em.getTransaction();
            et.begin();
            rennstrecke = em.find(Rennstrecke.class, rennstreckeId);
            rennstrecke.setOrt(ort);
            rennstrecke.setBundesland(bundesland);
            rennstrecke.setRennen(rennen);


            em.persist(rennstrecke);
            et.commit();
            System.out.println("Rennstrecke erfolgreich upgedated: " + rennstrecke);
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println(e.getMessage());
            }
        } finally {
            em.close();
        }
    }*/

    public static void rennstreckeLoeschen(int rennstreckenId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Rennstrecke rennstrecke = null;

        try {
            et = em.getTransaction();
            et.begin();
            rennstrecke = em.find(Rennstrecke.class, rennstreckenId);
            em.remove(rennstrecke);
            et.commit();
            System.out.format("Rennstrecke %d erfolgreich gelöscht.\n", rennstreckenId);
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println(e.getMessage());
            }
        } finally {
            em.close();
        }
    }
}
