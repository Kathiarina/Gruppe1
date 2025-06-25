package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity(name = "Rennstrecke")
@Table(name = "rennstrecke")
public class Rennstrecke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennstreckenId", updatable = false, nullable = false)
    private int rennstreckeId;

    @Column(name = "ort", nullable = false, length = 30)
    private String ort;

    @Column(name = "bundesland", nullable = false, length = 30)
    private String bundesland;

    @OneToMany(mappedBy = "rennstrecke")
    List<Rennen> rennen = new ArrayList<>();

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
        return rennstreckeId;
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

    @Override
    public String toString() {
        return String.format("Rennstrecke: Ort: %s, Bundesland: %s", this.ort, this.bundesland);
    }
}

