package at.fhburgenland.service;

import at.fhburgenland.model.Nationalitaet;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class NationalitaetService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    private static Scanner scanner = new Scanner(System.in);

    public static void nationalitaetHinzufuegen(String nationalitaetsBeschreibung) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Nationalitaet nationalitaet = new Nationalitaet(nationalitaetsBeschreibung);
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

    public static void alleNationalitaetenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT n FROM Nationalitaet n";
        TypedQuery<Nationalitaet> tq = em.createQuery(query, Nationalitaet.class);

        List<Nationalitaet> nationalitaetenListe = null;

        try {
            nationalitaetenListe = tq.getResultList();
            for (Nationalitaet nationalitaet : nationalitaetenListe) {
                System.out.println("Nationalität Nr: " + nationalitaet.getNationalitaetsId() + ", " + nationalitaet.getNationalitaetsBeschreibung());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static Nationalitaet nationalitaetAuswaehlen() {
        alleNationalitaetenAnzeigen();
        System.out.println("Bitte gib die ID der gewünschten Nationalität ein:");
        int nationalitätsId = scanner.nextInt();
        scanner.nextLine();
        EntityManager em = EMF.createEntityManager();
        Nationalitaet ausgewaehlteNationalität = null;
        try {
            ausgewaehlteNationalität = em.find(Nationalitaet.class, nationalitätsId);
            if (ausgewaehlteNationalität == null) {
                System.err.println("Keine Nationalität mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ausgewaehlteNationalität;
    }

    public static void nationalitaetLoeschen(int nationalitaetsId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Nationalitaet nationalitaet = null;

        try {
            et = em.getTransaction();
            et.begin();
            nationalitaet = em.find(Nationalitaet.class, nationalitaetsId);
            em.remove(nationalitaet);
            et.commit();
            System.out.format("Nationalität %d erfolgreich gelöscht.\n", nationalitaetsId);
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
