package at.fhburgenland.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Team")
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamId", updatable = false, nullable = false)
    private int teamId;

    @Column(name = "teamName", nullable = false, length = 60)
    private String teamName;

    @Column(name = "gruendungsjahr", nullable = false)
    private int gruendungsjahr;

    @OneToMany(mappedBy = "team")
    List<Fahrzeug> fahrzeug = new ArrayList<>();

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

    @Override
    public String toString() {
        return String.format("Team: %s, Gründungsjahr %d, %s, %s", this.teamName, this.gruendungsjahr, this.nationalitaet, this.hauptsponsor);
    }
}
