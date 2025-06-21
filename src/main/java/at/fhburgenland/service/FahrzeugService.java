package at.fhburgenland.service;

import at.fhburgenland.model.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class FahrzeugService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    private static Scanner scanner = new Scanner(System.in);

    public static void fahrzeugHinzufuegen(Fahrzeugtyp fahrzeugtyp, Team team) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Fahrzeug fahrzeug = new Fahrzeug(fahrzeugtyp, team);
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

    public static void alleFahrzeugeAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT fz FROM Fahrzeug fz";
        TypedQuery<Fahrzeug> tq = em.createQuery(query, Fahrzeug.class);

        List<Fahrzeug> fahrzeugListe = null;

        try {
            fahrzeugListe = tq.getResultList();
            for (Fahrzeug fahrzeug : fahrzeugListe) {
                System.out.println("Fahrzeug Nr: " + fahrzeug.getFahrzeugId() + ", " + fahrzeug.getFahrzeugtyp() + ", " + fahrzeug.getTeam());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static Fahrzeug fahrzeugAuswaehlen() {
        alleFahrzeugeAnzeigen();
        System.out.println("Bitte gib die ID des gewünschten Fahrzeugs ein:");
        int fahrzeugId = scanner.nextInt();
        scanner.nextLine();
        EntityManager em = EMF.createEntityManager();
        Fahrzeug ausgewaehltesFahrzeug = null;
        try {
            ausgewaehltesFahrzeug = em.find(Fahrzeug.class, fahrzeugId);
            if (ausgewaehltesFahrzeug == null) {
                System.err.println("Kein Fahrzeug mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ausgewaehltesFahrzeug;
    }

    public static void fahrzeugLoeschen(int fahrzeugId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Fahrzeug fahrzeug = null;

        try {
            et = em.getTransaction();
            et.begin();
            fahrzeug = em.find(Fahrzeug.class, fahrzeugId);
            em.remove(fahrzeug);
            et.commit();
            System.out.format("Fahrzeug %d erfolgreich gelöscht.\n", fahrzeugId);
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
