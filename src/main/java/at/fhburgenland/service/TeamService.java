package at.fhburgenland.service;

import at.fhburgenland.model.*;
import jakarta.persistence.*;

import java.util.List;


public class TeamService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static void teamHinzufuegen(Team team) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
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

    public static List<Team> alleTeamsAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT t FROM Team t";
        TypedQuery<Team> tq = em.createQuery(query, Team.class);

        List<Team> teamsListe = null;

        try {
            teamsListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return teamsListe;
    }

    public static Team teamAnzeigenNachId(int teamId) {
        EntityManager em = EMF.createEntityManager();
        Team team = null;

        try {
            team = em.find(Team.class, teamId);
            if (team != null) {
                System.out.println("Team gefunden:");
                System.out.println("ID: " + team.getTeamId());
                System.out.println("Teamname: " + team.getTeamName());
                System.out.println("Gründungsjahr: " + team.getGruendungsjahr());
                System.out.println("Hauptsponsor: " + team.getHauptsponsor());
                System.out.println("Nationalität: " + team.getNationalitaet());
            } else {
                System.err.println("Kein Team mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return team;
    }

    public static void teamUpdaten(Team team) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(team);
            et.commit();
            System.out.println("Team erfolgreich aktualisiert: " + team);

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void teamLoeschen(int teamId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
           Team team = em.find(Team.class, teamId);
            if (team != null) {
                em.remove(team);
                et.commit();
            } else {
                System.err.println("Team nicht gefunden.");
            }
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Teams.");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }
}
