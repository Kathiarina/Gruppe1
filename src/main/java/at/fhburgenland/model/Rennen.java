package at.fhburgenland.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Rennen")
@Table(name = "rennen")
public class Rennen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennenId", updatable = false, nullable = false)
    private int rennenId;

    @Column(name = "datumUhrzeit", nullable = false)
    private LocalDateTime datumUhrzeit;

    @ManyToOne
    @JoinColumn(name = "rennstreckenId", nullable = false)
    private Rennstrecke rennstrecke;

    @OneToMany(mappedBy = "rennen")
    private List<RennenFahrer> fahrerZuordnungen;

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

    public List<RennenFahrer> getFahrerZuordnungen() {
        return fahrerZuordnungen;
    }

    public void setDatumUhrzeit(LocalDateTime datumUhrzeit) {
        this.datumUhrzeit = datumUhrzeit;
    }

    public void setRennstrecke(Rennstrecke rennstrecke) {
        this.rennstrecke = rennstrecke;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formatiertesDatum = datumUhrzeit.format(formatter);
        return String.format("Rennen: %s, %s", formatiertesDatum, this.rennstrecke);
    }
}
