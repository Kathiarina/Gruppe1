package tables;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Rennstrecke")
@Table(name = "rennstrecke")
public class Rennstrecke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennstrecken_id", updatable = false, nullable = false)
    private int rennstrecken_id;

    @Column(name = "ort", nullable = false, length = 30)
    private String ort;

    @Column(name = "bundesland", nullable = false, length = 30)
    private String bundesland;

    @OneToMany(mappedBy = "rennstrecken_id")
    List<Rennen> rennen = new ArrayList<>();

    public Rennstrecke() {}

    public Rennstrecke(String ort, String bundesland){
        this.ort = ort;
        this.bundesland = bundesland;
    }

    public void setRennen(List<Rennen> rennen){this.rennen = rennen;}

    public List<Rennen> getRennen(){return rennen;}

    public String getOrt() {
        return ort;
    }

    public String getBundesland() {
        return bundesland;
    }

    public int getRennstrecken_id() {
        return rennstrecken_id;
    }



    // TODO Implement body of Rennstrecke
}
