package at.fhburgenland.model;
import jakarta.persistence.*;
/**
 * Repräsentiert ein Fahrzeug
 * Jedes Fahrzeug ist einem Fahrzeugtyp und einem Team zugeordnet
 * Ein Fahrzeug kann genau einem Fahrer zugeordnet sein
 */
@Entity(name = "Fahrzeug")
@Table(name = "fahrzeug")
public class Fahrzeug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrzeugId", updatable = false, nullable = false)
    private int fahrzeugId;

    /**
     * Mehrere Fahrzeuge können denselben Fahrzeugtyp haben (1:n Beziehung)
     */
    @ManyToOne
    @JoinColumn(name = "fahrzeugtypId", nullable = false)
    private Fahrzeugtyp fahrzeugtyp;

    /**
     * Mehrere Fahrzeuge können demselben Team zugewiesen sein (1:n Beziehung)
     */
    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    /**
     * Ein Fahrzeug kann von genau einem Fahrer genutzt werden (1:1 Beziehung)
     */
    @OneToOne(mappedBy = "fahrzeug")
    private Fahrer fahrer;

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
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

    public Fahrer getFahrer() {
        return fahrer;
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

    /**
     * Gibt eine textuelle Darstellung des Fahrzeugs zurück
     */
    @Override
    public String toString() {
        return String.format("Fahrzeug: %s, %s", this.fahrzeugtyp, this.team);
    }
}
