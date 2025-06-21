package at.fhburgenland.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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

    @Override
    public String toString() {
        return String.format("Rennen: %s, %s", this.datumUhrzeit, this.rennstrecke);
    }
}
