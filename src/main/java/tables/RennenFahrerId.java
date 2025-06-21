package tables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RennenFahrerId implements Serializable {

    @Column(name = "fahrer_id", updatable = false, nullable = false)
    private int fahrer_id;

    @Column(name = "rennen_id", updatable = false, nullable = false)
    private int rennen_id;
    // TODO Fields of RennenFahrerId

    public RennenFahrerId() {
        // TODO Initialization of fields of RennenFahrerId
    }

    public RennenFahrerId(int rennen_id, int fahrer_id) {
        this.rennen_id = rennen_id;
        this.fahrer_id = fahrer_id;
    }

    // TODO Implement body of RennenFahrerId
}
