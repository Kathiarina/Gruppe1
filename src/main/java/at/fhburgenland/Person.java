package at.fhburgenland;
import jakarta.persistence.*;

@Entity(name = "Person")
@Table(name = "person")

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pnr", updatable = false, nullable = false)
    private int pnr;

    @Column(name = "vorname", nullable = false, length = 30)
    private String vorname;

    @Column(name = "nachname", nullable = false, length = 30)
    private String nachname;

    @Column(name = "gehalt", nullable = false)
    private int gehalt;

    public Person() {
        // TODO Initialization of fields of Person
    }

    public Person(String vorname, String nachname, int gehalt) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.gehalt = gehalt;
    }

    public int getPnr(){
        return pnr;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public int getGehalt() {
        return gehalt;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setGehalt(int gehalt) {
        this.gehalt = gehalt;
    }
}
