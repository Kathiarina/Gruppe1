package at.fhburgenland.service;

import at.fhburgenland.model.Fahrzeugtyp;
import at.fhburgenland.model.Rennstrecke;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class FahrzeugtypService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    private static Scanner scanner = new Scanner(System.in);

    public static void fahrzeugtypHinzufuegen(String modell, String motor, int gewichtKg) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp(modell, motor, gewichtKg);
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

    public static void alleFahrzeugtypenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT ft FROM Fahrzeugtyp ft";
        TypedQuery<Fahrzeugtyp> tq = em.createQuery(query, Fahrzeugtyp.class);

        List<Fahrzeugtyp> fahrzeugtypListe = null;

        try {
            fahrzeugtypListe = tq.getResultList();
            for (Fahrzeugtyp fahrzeugtyp : fahrzeugtypListe) {
                System.out.println("Fahrzeugtyp Nr: " + fahrzeugtyp.getFahrzeugtypId() + ", Modell " + fahrzeugtyp.getModell() + ", Motor " + fahrzeugtyp.getMotor() + ", Gewicht " + fahrzeugtyp.getGewichtKg() + "kg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static Fahrzeugtyp fahrzeugtypAuswaehlen() {
        alleFahrzeugtypenAnzeigen();
        System.out.println("Bitte gib die ID des gewünschten Fahrzeugtyps ein:");
        int fahrzeugtypId = scanner.nextInt();
        scanner.nextLine();
        EntityManager em = EMF.createEntityManager();
        Fahrzeugtyp ausgewaehlterFahrzeugtyp = null;
        try {
            ausgewaehlterFahrzeugtyp = em.find(Fahrzeugtyp.class, fahrzeugtypId);
            if (ausgewaehlterFahrzeugtyp == null) {
                System.err.println("Kein Fahrzeugtyp mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ausgewaehlterFahrzeugtyp;
    }

    public static void fahrzeugtypLoeschen(int fahrzeugtypId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Fahrzeugtyp fahrzeugtyp = null;

        try {
            et = em.getTransaction();
            et.begin();
            fahrzeugtyp = em.find(Fahrzeugtyp.class, fahrzeugtypId);
            em.remove(fahrzeugtyp);
            et.commit();
            System.out.format("Fahrzeugtyp %d erfolgreich gelöscht.\n", fahrzeugtypId);
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
