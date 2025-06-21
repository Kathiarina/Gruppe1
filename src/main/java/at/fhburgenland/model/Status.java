package at.fhburgenland.model;

import jakarta.persistence.*;

@Entity(name = "Status")
@Table(name = "status")

public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusId", updatable = false, nullable = false)
    private int statusId;

    @Column(name = "statusBeschreibung", updatable = false, nullable = false)
    private String statusBeschreibung;

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

    public void setStatusBeschreibung(String statusBeschreibung) {
        this.statusBeschreibung = statusBeschreibung;
    }

    @Override
    public String toString() {
        return String.format("Status: %s", this.statusBeschreibung);
    }
}
