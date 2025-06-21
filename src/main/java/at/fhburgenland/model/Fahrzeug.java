package at.fhburgenland.model;
import jakarta.persistence.*;

@Entity(name = "Fahrzeug")
@Table(name = "fahrzeug")
public class Fahrzeug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrzeugId", updatable = false, nullable = false)
    private int fahrzeugId;

    @OneToOne
    @JoinColumn(name = "fahrzeugtypId", nullable = false)
    private Fahrzeugtyp fahrzeugtyp;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @OneToOne(mappedBy = "fahrzeug")
    private Fahrer fahrer;

    public Fahrzeug() {
    }

    public Fahrzeug(Fahrzeugtyp fahrzeugtyp, Team team) {
        this.fahrzeugtyp = fahrzeugtyp;
        this.team = team;
    }

    public int getFahrzeugId() {
        return fahrzeugId;
    }

    public Fahrzeugtyp getFahrzeugtyp() {
        return fahrzeugtyp;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setFahrzeugtyp(Fahrzeugtyp fahrzeugtyp) {
        this.fahrzeugtyp = fahrzeugtyp;
    }

    @Override
    public String toString() {
        return String.format("Fahrzeug: %s, %s", this.fahrzeugtyp, this.team);
    }
}
