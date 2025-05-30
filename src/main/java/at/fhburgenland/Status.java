package at.fhburgenland;

import jakarta.persistence.*;

@Entity(name = "Status")
@Table(name = "status")

public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", updatable = false, nullable = false)
    private int status_id;

    @Column(name = "beschreibung", updatable = false, nullable = false)
    private String beschreibung;
    // TODO Fields of Status

    public Status() {
        // TODO Initialization of fields of Status
    }

    // TODO Implement body of Status
}
