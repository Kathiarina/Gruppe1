package tables;

import jakarta.persistence.*;

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

    public Hauptsponsor() {

    }

}
