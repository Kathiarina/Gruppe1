package tables;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity(name = "Rennen")
@Table(name = "rennen")
public class Rennen {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennen_id", updatable = false, nullable = false)
    private int rennen_id;

    @Column(name = "datum_uhrzeit", nullable = false)
    private LocalDateTime datum_uhrzeit;

    @ManyToOne
    @JoinColumn(name = "rennstrecken_id", nullable = false)
    private Rennstrecke rennstrecke;

    public Rennen() {
    }

    public Rennen(LocalDateTime datum_uhrzeit, Rennstrecke rennstrecke) {
        this.datum_uhrzeit = datum_uhrzeit;
        this.rennstrecke = rennstrecke;
    }

    public int getRennen_id() {
        return rennen_id;
    }

    public LocalDateTime getDatum_uhrzeit() {
        return datum_uhrzeit;
    }

    public Rennstrecke getRennstrecke() {
        return rennstrecke;
    }

    public void setDatum_uhrzeit(LocalDateTime datum_uhrzeit) {
        this.datum_uhrzeit = datum_uhrzeit;
    }

    public void setRennstrecke(Rennstrecke rennstrecke) {
        this.rennstrecke = rennstrecke;
    }

    public static void rennenHinzufuegen(LocalDateTime datum_uhrzeit, Rennstrecke rennstrecke) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Rennen rennen = new Rennen(datum_uhrzeit, rennstrecke);
            System.out.println("Neues Rennen wurde angelegt: " + rennen);
            em.persist(rennen);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void alleRennenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT r FROM Rennen r";
        TypedQuery<Rennen> tq = em.createQuery(query, Rennen.class);

        List<Rennen> rennenListe = null;

        try {
            rennenListe = tq.getResultList();
            for (Rennen rennen : rennenListe) {
                System.out.println("Rennen Nr: " + rennen.getRennen_id() + ", Datum und Uhrzeit " + rennen.getDatum_uhrzeit() + ", " + rennen.getRennstrecke());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void rennenLoeschen(int rennen_id) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Rennen rennen = null;

        try {
            et = em.getTransaction();
            et.begin();
            rennen = em.find(Rennen.class, rennen_id);
            em.remove(rennen);
            et.commit();
            System.out.format("Rennen %d erfolgreich gelöscht.\n", rennen_id);
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println(e.getMessage());
            }
        } finally {
            em.close();
        }
    }

    @Override
    public String toString() {
        return String.format("Rennen: %s, %s", this.datum_uhrzeit, this.rennstrecke);
    }
}
