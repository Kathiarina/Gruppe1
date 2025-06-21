package tables;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Fahrzeugtyp")
@Table(name = "fahrzeugtyp")
public class Fahrzeugtyp {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrzeugtypId", updatable = false, nullable = false)
    private int fahrzeugtypId;

    @Column(name = "modell", nullable = false, length = 60)
    private String modell;

    @Column(name = "motor", nullable = false, length = 60)
    private String motor;

    @Column(name = "gewichtKg", nullable = false)
    private int gewichtKg;


    public Fahrzeugtyp() {
    }

    public Fahrzeugtyp(String modell, String motor, int gewichtKg) {
        this.modell = modell;
        this.motor = motor;
        this.gewichtKg = gewichtKg;
    }

    public int getFahrzeugtypId() {
        return fahrzeugtypId;
    }

    public String getModell() {
        return modell;
    }

    public String getMotor() {
        return motor;
    }

    public int getGewichtKg() {
        return gewichtKg;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public void setGewichtKg(int gewichtKg) {
        this.gewichtKg = gewichtKg;
    }

    public static void fahrzeugtypHinzufuegen(String modell, String motor, int gewichtKg) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp(modell, motor, gewichtKg);
            System.out.println("Neuer Fahrzeugtyp wurde angelegt: " + fahrzeugtyp);
            em.persist(fahrzeugtyp);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void alleFahrzeugtypenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT ft FROM Fahrzeugtyp ft";
        TypedQuery<Fahrzeugtyp> tq = em.createQuery(query, Fahrzeugtyp.class);

        List<Fahrzeugtyp> fahrzeugtypListe = null;

        try {
            fahrzeugtypListe = tq.getResultList();
            for (Fahrzeugtyp fahrzeugtyp : fahrzeugtypListe) {
                System.out.println("Fahrzeugtyp Nr: " + fahrzeugtyp.getFahrzeugtypId() + ", Modell " + fahrzeugtyp.getModell() + ", Motor " + fahrzeugtyp.getMotor() + ", Gewicht " + fahrzeugtyp.getGewichtKg() + "kg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void fahrzeugtypLoeschen(int fahrzeugtypId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Fahrzeugtyp fahrzeugtyp = null;

        try {
            et = em.getTransaction();
            et.begin();
            fahrzeugtyp = em.find(Fahrzeugtyp.class, fahrzeugtypId);
            em.remove(fahrzeugtyp);
            et.commit();
            System.out.format("Fahrzeugtyp %d erfolgreich gelöscht.\n", fahrzeugtypId);
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
        return String.format("Fahrzeugtyp: Modell %s, Motor %s, Gewicht %dkg", this.modell, this.motor, this.gewichtKg);
    }
}
