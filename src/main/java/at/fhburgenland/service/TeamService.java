package at.fhburgenland.service;

import at.fhburgenland.model.*;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Team-Entitäten
 * Beinhaltet CRUD-Methoden und Überprüfung von Verknüpfungen
 */
public class TeamService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Speichert ein neues Team in der Datenbank
     *
     * @param team Team, das gespeichert wird
     */
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
            System.err.println("Fehler beim Speichern des Teams: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Gibt alle Teams aus der Datenbank zurück
     *
     * @return Liste aller Teams
     */
    public static List<Team> alleTeamsAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT t FROM Team t";
        TypedQuery<Team> tq = em.createQuery(query, Team.class);

        List<Team> teamsListe = null;

        try {
            teamsListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return teamsListe;
    }

    /**
     * Sucht ein Ream anhand der ID und gibt es zurück und seine Informationen in der Konsole aus
     *
     * @param teamId ID des Teams
     * @return Gefundenes Team oder null
     */
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
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return team;
    }

    /**
     * Aktualisiert ein Team in der Datenbank
     *
     * @param team Team mit aktualisierten Werten
     */
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
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Löscht ein Team anhand der ID, wenn keine Verknüpfungen bestehen
     *
     * @param teamId ID des Teams, das gelöscht wird
     */
    public static void teamLoeschen(int teamId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Team team = em.find(Team.class, teamId);
            if (team == null) {
                System.err.println("Team nicht gefunden.");
                et.rollback();
                return;
            }
            String begruendung = pruefeVerknuepfungMitTeam(team);
            if (begruendung != null) {
                System.err.println(begruendung);
                et.rollback();
                return;
            }

            em.remove(team);
            et.commit();

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Teams." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }

    /**
     * Prüft, ob ein Hauptsponsor bereits einem anderen Team zugeordnet ist
     *
     * @param hauptsponsor       der zu überprüfende Hauptsponsor
     * @param ausgenommeneTeamId ID des Teams, das ausgenommen werden soll
     * @return true, wenn Hauptsponsor vergeben ist, sonst false
     */
    public static boolean hauptsponsorBereitsVergeben(Hauptsponsor hauptsponsor, int ausgenommeneTeamId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            String jpql = "SELECT COUNT(t) FROM Team t WHERE t.hauptsponsor = :hauptsponsor AND t.teamId != :id";
            int count = em.createQuery(jpql, int.class)
                    .setParameter("hauptsponsor", hauptsponsor)
                    .setParameter("id", ausgenommeneTeamId)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return false;
    }

    /**
     * Prüft, ob ein Team mit anderen Entitäten verknüpft ist
     * Wird verwendet, um das Löschen abzusichern
     *
     * @param team Team, das überprüft wird
     * @return Rückgabe einer Begründung als String oder null, wenn keine Verknüpfung besteht
     */
    public static String pruefeVerknuepfungMitTeam(Team team) {
        if (team == null) {
            return "Team ist null. Prüfung nicht möglich.";
        }

        try {
            boolean hatNationalitaet = team.getNationalitaet() != null;
            boolean hatHauptsponsor = team.getHauptsponsor() != null;
            boolean besitztFahrzeug = team.getFahrzeug() != null && !team.getFahrzeug().isEmpty();

            if (hatNationalitaet || hatHauptsponsor || besitztFahrzeug) {
                StringBuilder grund = new StringBuilder("Team kann nicht gelöscht werden, da es ");
                boolean first = true;
                if (hatNationalitaet) {
                    grund.append("einer Nationalität");
                    first = false;
                }
                if (hatHauptsponsor) {
                    if (!first) grund.append(" & ");
                    grund.append("einem Hauptsponsor");
                    first = false;
                }
                if (besitztFahrzeug) {
                    if (!first) grund.append(" & ");
                    grund.append("einem Fahrzeug");
                }
                grund.append(" zugeordnet ist.");
                return grund.toString();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
