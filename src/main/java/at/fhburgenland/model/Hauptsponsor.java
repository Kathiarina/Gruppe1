package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

@Entity(name = "Hauptsponsor")
@Table(name = "hauptsponsor")
public class Hauptsponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sponsorId", updatable = false, nullable = false)
    private int sponsorId;

    @Column(name = "sponsorName", nullable = false, length = 30)
    private String sponsorName;

    @Column(name = "jaehrlicheSponsorsumme", updatable = true, nullable = false)
    private int jaehrlicheSponsorsumme;

    @OneToOne(mappedBy = "hauptsponsor")
    private Team team;

    public Hauptsponsor() {
    }

    public Hauptsponsor(String sponsorName, int jaehrlicheSponsorsumme) {
        this.sponsorName = sponsorName;
        this.jaehrlicheSponsorsumme = jaehrlicheSponsorsumme;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public int getJaehrlicheSponsorsumme() {
        return jaehrlicheSponsorsumme;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public void setJaehrlicheSponsorsumme(int jaehrlicheSponsorsumme) {
        this.jaehrlicheSponsorsumme = jaehrlicheSponsorsumme;
    }

    @Override
    public String toString() {
        return String.format("Hauptsponsor: %s, %d ", this.sponsorName, this.jaehrlicheSponsorsumme);
    }
}
