package tables;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Rennen")
@Table(name = "rennen")
public class Rennen {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennenId", updatable = false, nullable = false)
    private int rennenId;

    @Column(name = "datumUhrzeit", nullable = false)
    private LocalDateTime datumUhrzeit;

    @ManyToOne
    @JoinColumn(name = "rennstreckenId", nullable = false)
    private Rennstrecke rennstrecke;

    public Rennen() {
    }

    public Rennen(LocalDateTime datumUhrzeit, Rennstrecke rennstrecke) {
        this.datumUhrzeit = datumUhrzeit;
        this.rennstrecke = rennstrecke;
    }

    public int getRennenId() {
        return rennenId;
    }

    public LocalDateTime getDatumUhrzeit() {
        return datumUhrzeit;
    }

    public Rennstrecke getRennstrecke() {
        return rennstrecke;
    }

    public void setDatumUhrzeit(LocalDateTime datumUhrzeit) {
        this.datumUhrzeit = datumUhrzeit;
    }

    public void setRennstrecke(Rennstrecke rennstrecke) {
        this.rennstrecke = rennstrecke;
    }

    public static void rennenHinzufuegen(LocalDateTime datumUhrzeit, Rennstrecke rennstrecke) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Rennen rennen = new Rennen(datumUhrzeit, rennstrecke);
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
                System.out.println("Rennen Nr: " + rennen.getRennenId() + ", Datum und Uhrzeit " + rennen.getDatumUhrzeit() + ", " + rennen.getRennstrecke());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void rennenLoeschen(int rennenId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Rennen rennen = null;

        try {
            et = em.getTransaction();
            et.begin();
            rennen = em.find(Rennen.class, rennenId);
            em.remove(rennen);
            et.commit();
            System.out.format("Rennen %d erfolgreich gelöscht.\n", rennenId);
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
        return String.format("Rennen: %s, %s", this.datumUhrzeit, this.rennstrecke);
    }
}
