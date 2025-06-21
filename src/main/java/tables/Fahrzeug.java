package tables;
import jakarta.persistence.*;

@Entity(name = "Fahrzeug")
@Table(name = "fahrzeug")
public class Fahrzeug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrzeug_id", updatable = false, nullable = false)
    private int fahrzeug_id;

    @Column(name = "fahrzeugtyp_id", updatable = false, nullable = false)
    private int fahrzeugtyp_id;

    @Column(name = "team_id", updatable = false, nullable = false)
    private int team_id;
    // TODO Fields of Fahrzeug

    public Fahrzeug() {
        // TODO Initialization of fields of Fahrzeug
    }

    // TODO Implement body of Fahrzeug
}
