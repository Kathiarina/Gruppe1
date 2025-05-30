package at.fhburgenland;
import jakarta.persistence.*;

@Entity(name = "Nationalitaet")
@Table(name = "nationalitaet")
public class Nationalitaet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nationalitaets_id", updatable = false, nullable = false)
    private int nationalitaets_id;

    @Column(name = "beschreibung", updatable = false, nullable = false, length = 30)
    private String beschreibung;

    // TODO Fields of Nationalitaet

    public Nationalitaet() {
        // TODO Initialization of fields of Nationalitaet
    }

    // TODO Implement body of Nationalitaet
}
