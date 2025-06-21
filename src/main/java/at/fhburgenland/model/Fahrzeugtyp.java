package at.fhburgenland.model;
import jakarta.persistence.*;

@Entity(name = "Fahrzeugtyp")
@Table(name = "fahrzeugtyp")
public class Fahrzeugtyp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrzeugtypId", updatable = false, nullable = false)
    private int fahrzeugtypId;

    @Column(name = "modell", nullable = false, length = 60)
    private String modell;

    @Column(name = "motor", nullable = false, length = 60)
    private String motor;

    @Column(name = "gewichtKg", nullable = false)
    private int gewichtKg;

    @OneToOne(mappedBy = "fahrzeugtyp")
    private Fahrzeug fahrzeug;

    public Fahrzeugtyp() {
    }

    public Fahrzeugtyp(String modell, String motor, int gewichtKg) {
        this.modell = modell;
        this.motor = motor;
        this.gewichtKg = gewichtKg;
    }

    public int getFahrzeugtypId() {
        return fahrzeugtypId;
    }

    public String getModell() {
        return modell;
    }

    public String getMotor() {
        return motor;
    }

    public int getGewichtKg() {
        return gewichtKg;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public void setGewichtKg(int gewichtKg) {
        this.gewichtKg = gewichtKg;
    }

    @Override
    public String toString() {
        return String.format("Fahrzeugtyp: Modell %s, Motor %s, Gewicht %dkg", this.modell, this.motor, this.gewichtKg);
    }
}
