package at.fhburgenland;
import jakarta.persistence.*;
import java.time.Duration;

@Entity(name = "Rennen_Fahrer")
@Table(name = "rennen_fahrer")
public class Rennen_Fahrer {

    @Column(name = "fahrer_id", updatable = false, nullable = false)
    private int fahrer_id;

    @Column(name = "rennen_id", updatable = false, nullable = false)
    private int rennen_id;

    @Column(name = "status_id", updatable = false, nullable = false)
    private int status_id;

    @Column(name = "zeit", nullable = false)
    private Duration zeit;

    // TODO Fields of RennenFahrer

    public Rennen_Fahrer() {
        // TODO Initialization of fields of RennenFahrer
    }

    // TODO Implement body of RennenFahrer
}
