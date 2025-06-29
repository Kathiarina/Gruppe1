package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert einen Status im Rennen
*/
@Entity(name = "Status")
@Table(name = "status")

public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusId", updatable = false, nullable = false)
    private int statusId;

    @Column(name = "statusBeschreibung", updatable = false, nullable = false)
    private String statusBeschreibung;

    /**
     * Liste der Rennergebnisse, die einen Status haben (1:n Beziehung)
     */
    @OneToMany(mappedBy = "status")
    private List<RennenFahrer> rennenFahrerList = new ArrayList<>();

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
    public Status() {

    }

    public Status(String statusBeschreibung) {
        this.statusBeschreibung = statusBeschreibung;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getStatusBeschreibung() {
        return statusBeschreibung;
    }

    /**
     * Gibt eine textuelle Darstellung des Status zurück
     */
    @Override
    public String toString() {
        return String.format("Status: %s", this.statusBeschreibung);
    }
}
