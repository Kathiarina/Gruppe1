package at.fhburgenland.model;

import jakarta.persistence.*;

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

    @OneToOne(mappedBy = "nationalitaet")
    private Team team;

    @OneToOne(mappedBy = "nationalitaet")
    private Fahrer fahrer;

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

    public void setNationalitaetsBeschreibung(String nationalitaetsBeschreibung) {
        this.nationalitaetsBeschreibung = nationalitaetsBeschreibung;
    }

    @Override
    public String toString() {
        return String.format("Nationalität: %s", this.nationalitaetsBeschreibung);
    }
}
