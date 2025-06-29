package at.fhburgenland.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 * Repräsentiert ein Rennen
 * Ein Rennen hat ein Datum und eine Uhrzeit und eine zugehörige Rennstrecke
 * Jedes Rennen ist einer Rennstrecke zugeordnet und ist mit einer Zwischentabelle mit mehreren Fahrern verbunden
 */
@Entity(name = "Rennen")
@Table(name = "rennen")
public class Rennen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennenId", updatable = false, nullable = false)
    private int rennenId;

    @Column(name = "datumUhrzeit", nullable = false)
    private LocalDateTime datumUhrzeit;

    /**
     * Die Rennstrecke auf der das Rennen stattfindet (1:n Beziehung)
     */
    @ManyToOne
    @JoinColumn(name = "rennstreckenId", nullable = false)
    private Rennstrecke rennstrecke;

    /**
     * Liste der Rennergebnisse, die auf diesem Rennen stattgefunden haben
     */
    @OneToMany(mappedBy = "rennen")
    private List<RennenFahrer> fahrerZuordnungen;

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
    public Rennen() {
    }

    public Rennen(LocalDateTime datumUhrzeit, Rennstrecke rennstrecke) {
        this.datumUhrzeit = datumUhrzeit;
        this.rennstrecke = rennstrecke;
    }

    public int getRennenId() {
        return rennenId;
    }

    public LocalDateTime getDatumUhrzeit() {
        return datumUhrzeit;
    }

    public Rennstrecke getRennstrecke() {
        return rennstrecke;
    }

    public void setDatumUhrzeit(LocalDateTime datumUhrzeit) {
        this.datumUhrzeit = datumUhrzeit;
    }

    public void setRennstrecke(Rennstrecke rennstrecke) {
        this.rennstrecke = rennstrecke;
    }

    /**
     * Gibt eine textuelle Darstellung des Rennens zurück
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formatiertesDatum = datumUhrzeit.format(formatter);
        return String.format("Rennen: %s, %s", formatiertesDatum, this.rennstrecke);
    }
}
