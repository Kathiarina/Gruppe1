package at.fhburgenland;
import jakarta.persistence.*;

@Entity(name = "Fahrzeugtyp")
@Table(name = "fahrzeugtyp")
public class Fahrzeugtyp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrzeugtyp_id", updatable = false, nullable = false)
    private int fahrzeugtyp_id;

    @Column(name = "modell", nullable = false, length = 60)
    private String modell;

    @Column(name = "motor", nullable = false, length = 60)
    private String motor;

    @Column(name = "gewicht_kg", nullable = false)
    private int gewicht_kg;
    // TODO Fields of Fahrzeugtyp

    public Fahrzeugtyp() {
        // TODO Initialization of fields of Fahrzeugtyp
    }

    // TODO Implement body of Fahrzeugtyp
}
