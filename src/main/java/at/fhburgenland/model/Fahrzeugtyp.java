package at.fhburgenland.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Repräsentiert einen Fahrzeugtyp
 * Ein Fahrzeugtyp beschreibt technische Merkmale wie Modell, Motor und Gewicht
 * Ein Fahrzeugtyp kann mehreren Fahrzeugen zugeordnet sein
 */
@Entity(name = "Fahrzeugtyp")
@Table(name = "fahrzeugtyp")
public class Fahrzeugtyp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrzeugtypId", updatable = false, nullable = false)
    private int fahrzeugtypId;

    @Column(name = "modell", nullable = false, length = 60)
    private String modell;

    @Column(name = "motor", nullable = false, length = 60)
    private String motor;

    @Column(name = "gewichtKg", nullable = false)
    private int gewichtKg;

    /**
     * Liste aller Fahrzeuge die diesem Typ zugeordnet sind (1:n Beziehung)
     */
    @OneToMany(mappedBy = "fahrzeugtyp", fetch = FetchType.EAGER)
    List<Fahrzeug> fahrzeug = new ArrayList<>();

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
    public Fahrzeugtyp() {
    }

    public Fahrzeugtyp(String modell, String motor, int gewichtKg) {
        this.modell = modell;
        this.motor = motor;
        this.gewichtKg = gewichtKg;
    }

    public int getFahrzeugtypId() {
        return fahrzeugtypId;
    }

    public String getModell() {
        return modell;
    }

    public String getMotor() {
        return motor;
    }

    public List<Fahrzeug> getFahrzeug() {
        return fahrzeug;
    }

    public int getGewichtKg() {
        return gewichtKg;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public void setGewichtKg(int gewichtKg) {
        this.gewichtKg = gewichtKg;
    }

    /**
     * Gibt eine textuelle Darstellung des Fahrzeugtyps zurück
     */
    @Override
    public String toString() {
        return String.format("Fahrzeugtyp: Modell %s, Motor %s, Gewicht %dkg", this.modell, this.motor, this.gewichtKg);
    }
}
