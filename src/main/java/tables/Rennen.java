package tables;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "Rennen")
@Table(name = "rennen")
public class Rennen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennen_id", updatable = false, nullable = false)
    private int rennen_id;

    @Column(name = "datum_uhrzeit", nullable = false)
    private Date datum_uhrzeit;

    @ManyToOne
    @JoinColumn(name = "rennstrecken_id", nullable = false)
    private Rennstrecke rennstrecke;

    public Rennen() {}

    public Rennen(Date datum_uhrzeit, Rennstrecke rennstrecke){
        this.datum_uhrzeit = datum_uhrzeit;
        this.rennstrecke = rennstrecke;
    }

    public int getRennen_id() {
        return rennen_id;
    }

    public Date getDatum_uhrzeit() {
        return datum_uhrzeit;
    }

    public Rennstrecke getRennstrecke() {
        return rennstrecke;
    }

    public void setDatum_uhrzeit(Date datum_uhrzeit) {
        this.datum_uhrzeit = datum_uhrzeit;
    }

    public void setRennstrecke(Rennstrecke rennstrecke) {
        this.rennstrecke = rennstrecke;
    }
}
