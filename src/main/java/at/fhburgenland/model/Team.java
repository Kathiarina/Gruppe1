package at.fhburgenland.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Repräsentiert ein Rennteam
 * Ein Team hat einen Namen, ein Gründungsjahr, eine Nationalität, mehrere Fahrzeuge und einen Hauptsponsor
 */
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

    /**
     * Liste der Fahrzeuge, die diesem Team zugeordnet sind (1:n Beziehung)
     */
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    List<Fahrzeug> fahrzeug = new ArrayList<>();

    /**
     * Hauptsponsor des Teams
     * Jedes Team hat genau einen Hauptsponsor (1:1 Beziehung)
     */
    @OneToOne
    @JoinColumn(name = "hauptsponsorId", nullable = false)
    private Hauptsponsor hauptsponsor;

    /**
     * Nationalität des Teams (1:n Beziehung)
     */
    @ManyToOne
    @JoinColumn(name = "nationalitaetsId", nullable = false)
    private Nationalitaet nationalitaet;

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
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

    public List<Fahrzeug> getFahrzeug() {
        return fahrzeug;
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

    /**
     * Gibt eine textuelle Darstellung des Teams zurück
     */
    @Override
    public String toString() {
        return String.format("Team: %s, Gründungsjahr %d, %s, %s", this.teamName, this.gruendungsjahr, this.nationalitaet, this.hauptsponsor);
    }
}
