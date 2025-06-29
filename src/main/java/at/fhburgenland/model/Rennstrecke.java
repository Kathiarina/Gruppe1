package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert eine Rennstrecke
 * Eine Rennstrecke hat Ort und Bundesland
 * Eine Rennstrecke kann mehrere Rennen austragen
 */
@Entity(name = "Rennstrecke")
@Table(name = "rennstrecke")
public class Rennstrecke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennstreckenId", updatable = false, nullable = false)
    private int rennstreckenId;

    @Column(name = "ort", nullable = false, length = 30)
    private String ort;

    @Column(name = "bundesland", nullable = false, length = 30)
    private String bundesland;

    /**
     * Liste der Rennen die auf dieser Rennstrecke ausgetragen werden (1:n Beziehung)
     */
    @OneToMany(mappedBy = "rennstrecke", fetch = FetchType.EAGER)
    List<Rennen> rennen = new ArrayList<>();

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
    public Rennstrecke() {
    }

    public Rennstrecke(String ort, String bundesland) {
        this.ort = ort;
        this.bundesland = bundesland;
    }

    public List<Rennen> getRennen() {
        return rennen;
    }

    public String getOrt() {
        return ort;
    }

    public String getBundesland() {
        return bundesland;
    }

    public int getRennstreckenId() {
        return rennstreckenId;
    }

    public void setRennen(List<Rennen> rennen) {
        this.rennen = rennen;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    /**
     * Gibt eine textuelle Darstellung der Rennstrecke zurück
     */
    @Override
    public String toString() {
        return String.format("Rennstrecke: Ort: %s, Bundesland: %s", this.ort, this.bundesland);
    }
}

