package tables;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity(name = "Rennstrecke")
@Table(name = "rennstrecke")
public class Rennstrecke {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    private static Scanner scanner = new Scanner(System.in);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rennstreckenId", updatable = false, nullable = false)
    private int rennstreckeId;

    @Column(name = "ort", nullable = false, length = 30)
    private String ort;

    @Column(name = "bundesland", nullable = false, length = 30)
    private String bundesland;

    @OneToMany(mappedBy = "rennstrecke")
    List<Rennen> rennen = new ArrayList<>();

    public Rennstrecke() {
    }

    public Rennstrecke(String ort, String bundesland) {
        this.ort = ort;
        this.bundesland = bundesland;
    }

    public void setRennen(List<Rennen> rennen) {
        this.rennen = rennen;
    }

    public List<Rennen> getRennen() {
        return rennen;
    }

    public String getOrt() {
        return ort;
    }

    public String getBundesland() {
        return bundesland;
    }

    public int getRennstreckenId() {
        return rennstreckeId;
    }

    public static void rennstreckeHinzufuegen(String ort, String bundesland) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Rennstrecke rennstrecke = new Rennstrecke(ort, bundesland);
            System.out.println("Neue Rennstrecke wurde angelegt: " + rennstrecke);
            em.persist(rennstrecke);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void alleRennstreckenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT rs FROM Rennstrecke rs";
        TypedQuery<Rennstrecke> tq = em.createQuery(query, Rennstrecke.class);

        List<Rennstrecke> rennstreckenListe = null;

        try {
            rennstreckenListe = tq.getResultList();
            for (Rennstrecke rennstrecke : rennstreckenListe) {
                System.out.println("Rennstrecke Nr: " + rennstrecke.getRennstreckenId() + ", Ort " + rennstrecke.getOrt() + ", Bundesland " + rennstrecke.getBundesland());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static Rennstrecke rennstreckeAuswaehlen(){
        alleRennstreckenAnzeigen();
        System.out.println("Bitte gib die ID der gewünschten Rennstrecke ein:");
        int rennstreckenId = scanner.nextInt();
        scanner.nextLine();
        EntityManager em = EMF.createEntityManager();
        Rennstrecke ausgewaehlteRennstrecke = null;
        try {
            ausgewaehlteRennstrecke = em.find(Rennstrecke.class, rennstreckenId);
            if (ausgewaehlteRennstrecke == null) {
                System.err.println("Keine Rennstrecke mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ausgewaehlteRennstrecke;
    }

    public static void rennstreckeLoeschen(int rennstreckenId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Rennstrecke rennstrecke = null;

        try {
            et = em.getTransaction();
            et.begin();
            rennstrecke = em.find(Rennstrecke.class, rennstreckenId);
            em.remove(rennstrecke);
            et.commit();
            System.out.format("Rennstrecke %d erfolgreich gelöscht.\n", rennstreckenId);
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
        return String.format("Rennstrecke %d: %s, %s", this.rennstreckeId, this.ort, this.bundesland);
    }
}

