package at.fhburgenland.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity(name = "RennenFahrer")
@Table(name = "rennenFahrer")
public class RennenFahrer {
    @EmbeddedId
    private RennenFahrerId rennenFahrerId;

    @ManyToOne
    @MapsId("fahrerId")
    @JoinColumn(name = "fahrerId")
    private Fahrer fahrer;

    @ManyToOne
    @MapsId("rennenId")
    @JoinColumn(name = "rennenId")
    private Rennen rennen;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status status;

    @Column(name = "zeit", nullable = false)
    private String zeit;

    public RennenFahrer() {
    }

    public RennenFahrer(Fahrer fahrer, Rennen rennen, Status status, String zeit) {
        this.fahrer = fahrer;
        this.rennen = rennen;
        this.status = status;
        this.zeit = zeit;
        this.rennenFahrerId = new RennenFahrerId(rennen.getRennenId(), fahrer.getFahrerId());
    }

    public Fahrer getFahrer() {
        return fahrer;
    }

    public Rennen getRennen() {
        return rennen;
    }

    public Status getStatus() {
        return status;
    }

    public String getZeit() {
        return zeit;
    }

    public RennenFahrerId getRennenFahrerId() {
        return rennenFahrerId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setZeit(String zeit) {
        this.zeit = zeit;
    }

    public void setFahrer(Fahrer fahrer) {
        this.fahrer = fahrer;
    }

    public void setRennen(Rennen rennen) {
        this.rennen = rennen;
    }

    @Override
    public String toString() {
        return String.format("Fahrer %s - Rennen %s - Zeit: %s - Status: %s",
                fahrer.getFahrerId(), rennen.getRennenId(), zeit, status.getStatusBeschreibung());
    }
}
