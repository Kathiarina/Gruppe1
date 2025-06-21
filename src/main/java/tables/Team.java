package tables;
import jakarta.persistence.*;

@Entity(name = "Team")
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", updatable = false, nullable = false)
    private int team_id;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "gruendungsjahr", nullable = false)
    private int gruendungsjahr;

    @Column(name = "nationalitaets_id", updatable = false, nullable = false)
    private int nationalitaets_id;

    @Column(name = "sponsor_id", updatable = false, nullable = false)
    private int sponsor_id;

    // TODO Fields of Team

    public Team() {
        // TODO Initialization of fields of Team
    }

    // TODO Implement body of Team
}
