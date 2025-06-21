package tables;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Nationalitaet")
@Table(name = "nationalitaet")
public class Nationalitaet {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nationalitaetsId", updatable = false, nullable = false)
    private int nationalitaetsId;

    @Column(name = "nationalitaetsBeschreibung", updatable = false, nullable = false, length = 30)
    private String nationalitaetsBeschreibung;

    @OneToOne(mappedBy = "team")
    private Team team;

    public Nationalitaet() {
    }

    public Nationalitaet(String nationalitaetsBeschreibung) {
        this.nationalitaetsBeschreibung = nationalitaetsBeschreibung;
    }

    public int getNationalitaetsId() {
        return nationalitaetsId;
    }

    public String getNationalitaetsBeschreibung() {
        return nationalitaetsBeschreibung;
    }

    public void setNationalitaetsBeschreibung(String nationalitaetsBeschreibung) {
        this.nationalitaetsBeschreibung = nationalitaetsBeschreibung;
    }

    public static void nationalitaetHinzufuegen(String nationalitaetsBeschreibung) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Nationalitaet nationalitaet = new Nationalitaet(nationalitaetsBeschreibung);
            System.out.println("Neue Nationalität wurde angelegt: " + nationalitaet);
            em.persist(nationalitaet);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void alleNationalitaetenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT n FROM Nationalitaet n";
        TypedQuery<Nationalitaet> tq = em.createQuery(query, Nationalitaet.class);

        List<Nationalitaet> nationalitaetenListe = null;

        try {
            nationalitaetenListe = tq.getResultList();
            for (Nationalitaet nationalitaet : nationalitaetenListe) {
                System.out.println("Nationalität Nr: " + nationalitaet.getNationalitaetsId() + ", " + nationalitaet.getNationalitaetsBeschreibung());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void nationalitaetLoeschen(int nationalitaetsId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Nationalitaet nationalitaet = null;

        try {
            et = em.getTransaction();
            et.begin();
            nationalitaet = em.find(Nationalitaet.class, nationalitaetsId);
            em.remove(nationalitaet);
            et.commit();
            System.out.format("Nationalität %d erfolgreich gelöscht.\n", nationalitaetsId);
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
        return String.format("Nationalität %d: %s", this.nationalitaetsId, this.nationalitaetsBeschreibung);
    }
}
