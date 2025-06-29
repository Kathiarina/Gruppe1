package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.List;
/**
 * Repräsentiert einen Fahrer
 * Ein Fahrer hat eine eindeutige ID, einen Vornamen, Nachnamen, eine Nationalität und genau ein Fahrzeug
 * Jeder Fahrer kann an mehreren Rennen teilnehmen (über die Zwischentabelle RennenFahrer)
 */
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

    /**
     * Jeder Fahrer besitzt genau ein Fahrzeug (1:1 Beziehung)
     */
    @OneToOne
    @JoinColumn(name = "fahrzeugId", nullable = false, unique = true)
    private Fahrzeug fahrzeug;

    /**
     * Mehrere Fahrer können dieselbe Nationalität haben (1:n Beziehung)
     */
    @ManyToOne
    @JoinColumn(name = "nationalitaetsId", nullable = false)
    private Nationalitaet nationalitaet;

    /**
     * Liste der Rennen an denen der Fahrer teilgenommen hat (1:n Beziehung zur Zwischentabelle)
     */
    @OneToMany(mappedBy = "fahrer")
    private List<RennenFahrer> rennenZuordnungen;

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
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

    public List<RennenFahrer> getRennenZuordnungen() {
        return rennenZuordnungen;
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

    /**
     * Gibt eine textuelle Darstellung des Fahrers zurück
     */
    @Override
    public String toString() {
        return String.format("Fahrer: Vorname %s, Nachname %s, %s, %s", this.vorname, this.nachname, this.nationalitaet, this.fahrzeug);
    }
}
