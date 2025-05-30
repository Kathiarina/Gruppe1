package at.fhburgenland;

import jakarta.persistence.*;

@Entity(name = "Hauptsponsor")
@Table(name = "hauptsponsor")
public class Hauptsponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sponsor_id", updatable = false, nullable = false)
    private int sponsor_id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "jaehrliche_sponsorsumme", updatable = true, nullable = false)
    private int jaehrliche_sponsorumme;

    // TODO Fields of Hauptsponsor

    public Hauptsponsor() {
        // TODO Initialization of fields of Hauptsponsor
    }

    // TODO Implement body of Hauptsponsor
}
