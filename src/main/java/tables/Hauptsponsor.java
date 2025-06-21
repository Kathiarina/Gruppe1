package tables;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Entity(name = "Hauptsponsor")
@Table(name = "hauptsponsor")
public class Hauptsponsor {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    private static Scanner scanner = new Scanner(System.in);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sponsorId", updatable = false, nullable = false)
    private int sponsorId;

    @Column(name = "sponsorName", nullable = false, length = 30)
    private String sponsorName;

    @Column(name = "jaehrlicheSponsorsumme", updatable = true, nullable = false)
    private int jaehrlicheSponsorsumme;

    @OneToOne(mappedBy = "hauptsponsor")
    private Team team;

    public Hauptsponsor() {
    }

    public Hauptsponsor(String sponsorName, int jaehrlicheSponsorsumme) {
        this.sponsorName = sponsorName;
        this.jaehrlicheSponsorsumme = jaehrlicheSponsorsumme;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public int getJaehrlicheSponsorsumme() {
        return jaehrlicheSponsorsumme;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public void setJaehrlicheSponsorsumme(int jaehrlicheSponsorsumme) {
        this.jaehrlicheSponsorsumme = jaehrlicheSponsorsumme;
    }

    public static void hauptsponsorHinzufuegen(String sponsorName, int jaehrlicheSponsorsumme) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Hauptsponsor hauptsponsor = new Hauptsponsor(sponsorName, jaehrlicheSponsorsumme);
            System.out.println("Neuer Hauptsponsor wurde angelegt: " + hauptsponsor);
            em.persist(hauptsponsor);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static Hauptsponsor hauptsponsorAuswaehlen() {
        alleHauptsponsorenAnzeigen();
        System.out.println("Bitte gib die ID des gewünschten Hauptsponsors ein:");
        int sponsorId = scanner.nextInt();
        scanner.nextLine();
        EntityManager em = EMF.createEntityManager();
        Hauptsponsor ausgewaehlterHauptsponsor = null;
        try {
            ausgewaehlterHauptsponsor = em.find(Hauptsponsor.class, sponsorId);
            if (ausgewaehlterHauptsponsor == null) {
                System.err.println("Kein Hauptsponsor mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ausgewaehlterHauptsponsor;
    }

    public static void alleHauptsponsorenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT hs FROM Hauptsponsor hs";
        TypedQuery<Hauptsponsor> tq = em.createQuery(query, Hauptsponsor.class);

        List<Hauptsponsor> hauptsponsorenListe = null;

        try {
            hauptsponsorenListe = tq.getResultList();
            for (Hauptsponsor hauptsponsor : hauptsponsorenListe) {
                System.out.println("Hauptsponsor Nr: " + hauptsponsor.getSponsorId() + ", Hauptsponsorname " + hauptsponsor.getSponsorName() + ", Jährliche Sponsorsumme " + hauptsponsor.getJaehrlicheSponsorsumme());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void hauptsponsorLoeschen(int hauptsponsorId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Hauptsponsor hauptsponsor = null;

        try {
            et = em.getTransaction();
            et.begin();
            hauptsponsor = em.find(Hauptsponsor.class, hauptsponsorId);
            em.remove(hauptsponsor);
            et.commit();
            System.out.format("Hauptsponsor %d erfolgreich gelöscht.\n", hauptsponsorId);
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
        return String.format("Hauptsponsor: %s, %d ", this.sponsorName, this.jaehrlicheSponsorsumme);
    }
}
