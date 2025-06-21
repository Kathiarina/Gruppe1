package at.fhburgenland.model;

import jakarta.persistence.*;

@Entity(name = "Fahrer")
@Table(name = "fahrer")
public class Fahrer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrerId", updatable = false, nullable = false)
    private int fahrerId;

    @Column(name = "vorname", nullable = false, length = 30)
    private String vorname;

    @Column(name = "nachname", nullable = false, length = 30)
    private String nachname;

    @OneToOne
    @JoinColumn(name = "fahrzeugId", nullable = false)
    private Fahrzeug fahrzeug;

    @OneToOne
    @JoinColumn(name = "nationalitaetsId", nullable = false)
    private Nationalitaet nationalitaet;

    public Fahrer() {
    }

    public Fahrer(String vorname, String nachname, Nationalitaet nationalitaet, Fahrzeug fahrzeug) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.nationalitaet = nationalitaet;
        this.fahrzeug = fahrzeug;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }

    public Nationalitaet getNationalitaet() {
        return nationalitaet;
    }

    public int getFahrerId() {
        return fahrerId;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    public void setNationalitaet(Nationalitaet nationalitaet) {
        this.nationalitaet = nationalitaet;
    }

    @Override
    public String toString() {
        return String.format("Fahrer: Vorname %s, Nachname %s, %s, %s", this.vorname, this.nachname, this.nationalitaet, this.fahrzeug);
    }
}
