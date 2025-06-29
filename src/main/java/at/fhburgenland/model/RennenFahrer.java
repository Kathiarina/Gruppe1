package at.fhburgenland.model;

import jakarta.persistence.*;

/**
 * Zwischentabelle zwischen Fahrer und Rennen
 * Repräsentiert die Teilnahme eines Fahrers an einem bestimmten Rennen
 * Hat Status und Zeit als zusätzliche Attribute, daher keine reine m:n Beziehung
 */
@Entity(name = "RennenFahrer")
@Table(name = "rennenFahrer")
public class RennenFahrer {
    @EmbeddedId
    private RennenFahrerId rennenFahrerId;

    /**
     * Fahrer, der an dem Rennen teilgenommen hat (1:n Beziehung)
     */
    @ManyToOne
    @MapsId("fahrerId")
    @JoinColumn(name = "fahrerId")
    private Fahrer fahrer;

    /**
     * Rennen, das stattgefunden hat (1:n Beziehung)
     */
    @ManyToOne
    @MapsId("rennenId")
    @JoinColumn(name = "rennenId")
    private Rennen rennen;

    /**
     * Status der Rennergebnisse (1:n Beziehung)
     */
    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status status;

    @Column(name = "zeit", nullable = true)
    private String zeit;

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
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

    public void setRennenFahrerId(RennenFahrerId rennenFahrerId) {
        this.rennenFahrerId = rennenFahrerId;
    }

    /**
     * Gibt eine textuelle Darstellung des Rennergebnisses zurück
     */
    @Override
    public String toString() {
        return String.format("Fahrer %s - Rennen %s - Zeit: %s - Status: %s",
                fahrer.getFahrerId(), rennen.getRennenId(), zeit, status.getStatusBeschreibung());
    }
}
