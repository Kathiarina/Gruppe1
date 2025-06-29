package at.fhburgenland.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Repräsentiert den zusammengesetzten Primärschlüssel für die Entität RennenFahrer
 * Hat FahrerId und RennenId
 */
@Embeddable
public class RennenFahrerId implements Serializable {

    @Column(name = "fahrerId", updatable = false, nullable = false)
    private int fahrerId;

    @Column(name = "rennenId", updatable = false, nullable = false)
    private int rennenId;

    /**
     * Konstruktoren und Getter und Setter für die Attribute
     */
    public RennenFahrerId() {
    }

    public RennenFahrerId(int rennenId, int fahrerId) {
        this.rennenId = rennenId;
        this.fahrerId = fahrerId;
    }

    public int getFahrerId() {
        return fahrerId;
    }

    public int getRennenId() {
        return rennenId;
    }

    /**
     * Überprüft die Gleichheit zweier RennenFahrerId-Objekte
     * Sie sind gleich, wenn die IDs übereinstimmen
     *
     * @param o Vergleichsobjekt
     * @return true, wenn beide IDs gleich sind
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RennenFahrerId that = (RennenFahrerId) o;
        return fahrerId == that.fahrerId && rennenId == that.rennenId;
    }

    /**
     * Gibt eine textuelle Darstellung der IDs zurück
     */
    @Override
    public String toString() {
        return String.format("RennenID: %s, FahrerID: %s", this.rennenId, this.fahrerId);
    }

    /**
     * Berechnet den Hashcode auf Basis der beiden IDs
     *
     * @return Hashcode der zusammengesetzten ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(fahrerId, rennenId);
    }
}
