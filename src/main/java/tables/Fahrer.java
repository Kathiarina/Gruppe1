package tables;
import jakarta.persistence.*;

@Entity(name = "Fahrer")
@Table(name = "fahrer")
public class Fahrer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrer_id", updatable = false, nullable = false)
    private int fahrer_id;

   @Column(name = "vorname", nullable = false, length = 30)
   private String vorname;

   @Column(name = "nachname", nullable = false, length = 30)
   private String nachname;

   @Column(name = "nationalitaets_id", updatable = false, nullable = false)
   private int nationalitaets_id;

   @Column(name = "fahrzeug_id", updatable = false, nullable = false)
   private int fahrzeug_id;

    // TODO Fields of Fahrer

    public Fahrer() {
        // TODO Initialization of fields of Fahrer
    }

    // TODO Implement body of Fahrer
}
