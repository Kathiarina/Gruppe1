package at.fhburgenland.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RennenFahrerId implements Serializable {

    @Column(name = "fahrerId", updatable = false, nullable = false)
    private int fahrerId;

    @Column(name = "rennenId", updatable = false, nullable = false)
    private int rennenId;

    public RennenFahrerId() {
    }

    public RennenFahrerId(int rennenId, int fahrerId) {
        this.rennenId = rennenId;
        this.fahrerId = fahrerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RennenFahrerId that = (RennenFahrerId) o;
        return fahrerId == that.fahrerId && rennenId == that.rennenId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fahrerId, rennenId);
    }
}
