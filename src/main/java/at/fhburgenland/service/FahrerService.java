package at.fhburgenland.service;

import at.fhburgenland.model.Fahrer;
import at.fhburgenland.model.Fahrzeug;
import at.fhburgenland.model.Nationalitaet;
import jakarta.persistence.*;

import java.util.List;

public class FahrerService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static void fahrerHinzufuegen(String vorname, String nachname, Nationalitaet nationalitaet, Fahrzeug fahrzeug) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Fahrer fahrer = new Fahrer(vorname, nachname, nationalitaet, fahrzeug);
            System.out.println("Neuer Fahrer wurde angelegt: " + fahrer);
            em.persist(fahrer);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void alleFahrerAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT f FROM Fahrer f";
        TypedQuery<Fahrer> tq = em.createQuery(query, Fahrer.class);

        List<Fahrer> fahrerListe = null;

        try {
            fahrerListe = tq.getResultList();
            for (Fahrer fahrer : fahrerListe) {
                System.out.println("Fahrer Nr: " + fahrer.getFahrerId() + ", Vorname " + fahrer.getVorname() + ", Nachname " + fahrer.getNachname() + ", " + fahrer.getNationalitaet() + ", " + fahrer.getFahrzeug());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void fahrerLoeschen(int fahrerId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Fahrer fahrer = null;

        try {
            et = em.getTransaction();
            et.begin();
            fahrer = em.find(Fahrer.class, fahrerId);
            em.remove(fahrer);
            et.commit();
            System.out.format("Fahrer %d erfolgreich gelöscht.\n", fahrerId);
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println(e.getMessage());
            }
        } finally {
            em.close();
        }
    }
}
