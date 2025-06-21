package tables;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Team")
@Table(name = "team")
public class Team {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamId", updatable = false, nullable = false)
    private int teamId;

    @Column(name = "teamName", nullable = false, length = 60)
    private String teamName;

    @Column(name = "gruendungsjahr", nullable = false)
    private int gruendungsjahr;

    @OneToOne
    @JoinColumn(name = "sponsorId", nullable = false)
    private Hauptsponsor hauptsponsor;

    @OneToOne
    @JoinColumn(name = "nationalitaetsId", nullable = false)
    private Nationalitaet nationalitaet;

    public Team() {
    }

    public Team(String teamName, int gruendungsjahr, Nationalitaet nationalitaet, Hauptsponsor hauptsponsor) {
        this.teamName = teamName;
        this.gruendungsjahr = gruendungsjahr;
        this.nationalitaet = nationalitaet;
        this.hauptsponsor = hauptsponsor;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getGruendungsjahr() {
        return gruendungsjahr;
    }

    public Hauptsponsor getHauptsponsor() {
        return hauptsponsor;
    }

    public Nationalitaet getNationalitaet() {
        return nationalitaet;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setGruendungsjahr(int gruendungsjahr) {
        this.gruendungsjahr = gruendungsjahr;
    }

    public void setHauptsponsor(Hauptsponsor hauptsponsor) {
        this.hauptsponsor = hauptsponsor;
    }

    public void setNationalitaet(Nationalitaet nationalitaet) {
        this.nationalitaet = nationalitaet;
    }

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
                System.out.println("Team Nr: " + team.getTeamId() + ", " + team.getTeamName() + ", Gründungsjahr " + team.getGruendungsjahr() + ", Nationalität " + team.getNationalitaet() + ", Hauptsponsor " + team.getHauptsponsor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
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

    @Override
    public String toString() {
        return String.format("Team %d: %s, Gründungsjahr %d, Nationalität ", this.teamId, this.teamName, this.gruendungsjahr, this.nationalitaet, this.hauptsponsor);
    }
}
