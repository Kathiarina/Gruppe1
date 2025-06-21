package at.fhburgenland.service;

import at.fhburgenland.model.Fahrzeugtyp;
import at.fhburgenland.model.Hauptsponsor;
import at.fhburgenland.model.Nationalitaet;
import at.fhburgenland.model.Team;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class TeamService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    private static Scanner scanner = new Scanner(System.in);

    public static void teamHinzufuegen(String teamName, int gruendungsjahr, Nationalitaet nationalitaet, Hauptsponsor hauptsponsor) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Team team = new Team(teamName, gruendungsjahr, nationalitaet, hauptsponsor);
            System.out.println("Neues Team wurde angelegt: " + team);
            em.persist(team);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void alleTeamsAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT t FROM Team t";
        TypedQuery<Team> tq = em.createQuery(query, Team.class);

        List<Team> teamsListe = null;

        try {
            teamsListe = tq.getResultList();
            for (Team team : teamsListe) {
                System.out.println("Team Nr: " + team.getTeamId() + ", " + team.getTeamName() + ", Gründungsjahr " + team.getGruendungsjahr() + ", " + team.getNationalitaet() + ", " + team.getHauptsponsor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static Team teamAuswaehlen() {
        alleTeamsAnzeigen();
        System.out.println("Bitte gib die ID des gewünschten Teams ein:");
        int teamId = scanner.nextInt();
        scanner.nextLine();
        EntityManager em = EMF.createEntityManager();
        Team ausgewaehltesTeam = null;
        try {
            ausgewaehltesTeam = em.find(Team.class, teamId);
            if (ausgewaehltesTeam == null) {
                System.err.println("Kein Team mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ausgewaehltesTeam;
    }

    public static void teamLoeschen(int teamId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Team team = null;

        try {
            et = em.getTransaction();
            et.begin();
            team = em.find(Team.class, teamId);
            em.remove(team);
            et.commit();
            System.out.format("Team %d erfolgreich gelöscht.\n", teamId);
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
