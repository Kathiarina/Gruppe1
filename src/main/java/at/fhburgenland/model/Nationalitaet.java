package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity(name = "Nationalitaet")
@Table(name = "nationalitaet")
public class Nationalitaet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nationalitaetsId", updatable = false, nullable = false)
    private int nationalitaetsId;

    @Column(name = "nationalitaetsBeschreibung", updatable = false, nullable = false, length = 30)
    private String nationalitaetsBeschreibung;

    @OneToMany(mappedBy = "nationalitaet", fetch = FetchType.EAGER)
    private List<Team> team = new ArrayList<>();

    @OneToMany(mappedBy = "nationalitaet", fetch = FetchType.EAGER)
    private List<Fahrer> fahrer = new ArrayList<>();

    public Nationalitaet() {
    }

    public Nationalitaet(String nationalitaetsBeschreibung) {
        this.nationalitaetsBeschreibung = nationalitaetsBeschreibung;
    }

    public int getNationalitaetsId() {
        return nationalitaetsId;
    }

    public String getNationalitaetsBeschreibung() {
        return nationalitaetsBeschreibung;
    }

    public List<Team> getTeam() {
        return team;
    }

    public List<Fahrer> getFahrer() {
        return fahrer;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }

    public void setFahrer(List<Fahrer> fahrer) {
        this.fahrer = fahrer;
    }

    public void setNationalitaetsBeschreibung(String nationalitaetsBeschreibung) {
        this.nationalitaetsBeschreibung = nationalitaetsBeschreibung;
    }

    @Override
    public String toString() {
        return String.format("Nationalität: %s", this.nationalitaetsBeschreibung);
    }
}
