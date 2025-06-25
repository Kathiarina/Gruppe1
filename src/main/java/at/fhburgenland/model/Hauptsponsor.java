package at.fhburgenland.model;

import jakarta.persistence.*;

@Entity(name = "Hauptsponsor")
@Table(name = "hauptsponsor")
public class Hauptsponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hauptsponsorId", updatable = false, nullable = false)
    private int hauptsponsorId;

    @Column(name = "hauptsponsorName", nullable = false, length = 30)
    private String hauptsponsorName;

    @Column(name = "jaehrlicheSponsorsumme", updatable = true, nullable = false)
    private int jaehrlicheSponsorsumme;

    @OneToOne(mappedBy = "hauptsponsor")
    private Team team;

    public Hauptsponsor() {
    }

    public Hauptsponsor(String hauptsponsorName, int jaehrlicheSponsorsumme) {
        this.hauptsponsorName = hauptsponsorName;
        this.jaehrlicheSponsorsumme = jaehrlicheSponsorsumme;
    }

    public int getHauptsponsorId() {
        return hauptsponsorId;
    }

    public String getHauptsponsorName() {
        return hauptsponsorName;
    }

    public int getJaehrlicheSponsorsumme() {
        return jaehrlicheSponsorsumme;
    }

    public Team getTeam() {
        return team;
    }

    public void setHauptsponsorName(String hauptsponsorName) {
        this.hauptsponsorName = hauptsponsorName;
    }

    public void setJaehrlicheSponsorsumme(int jaehrlicheSponsorsumme) {
        this.jaehrlicheSponsorsumme = jaehrlicheSponsorsumme;
    }

    @Override
    public String toString() {
        return String.format("Hauptsponsor: %s, %d€ ", this.hauptsponsorName, this.jaehrlicheSponsorsumme);
    }
}
