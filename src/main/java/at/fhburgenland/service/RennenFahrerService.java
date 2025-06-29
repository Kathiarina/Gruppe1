package at.fhburgenland.service;

import at.fhburgenland.model.RennenFahrer;
import at.fhburgenland.model.RennenFahrerId;
import jakarta.persistence.*;

import java.util.List;

public class RennenFahrerService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static void rennenFahrerHinzufuegen(RennenFahrer rennenFahrer) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            RennenFahrer vorhandenesErgebnis = em.find(RennenFahrer.class, rennenFahrer.getRennenFahrerId());
            if(vorhandenesErgebnis != null) {
                throw new IllegalStateException("Rennergebnis existiert bereits.");
            }
            et = em.getTransaction();
            et.begin();
            em.merge(rennenFahrer);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern des Ergebnisses: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static List<RennenFahrer> alleRennenFahrerAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT rf FROM RennenFahrer rf";
        TypedQuery<RennenFahrer> tq = em.createQuery(query, RennenFahrer.class);

        List<RennenFahrer> rennenFahrerListe = null;

        try {
            rennenFahrerListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return rennenFahrerListe;
    }

    public static RennenFahrer rennenFahrerAnzeigenNachId(RennenFahrerId rennenFahrerId) {
        EntityManager em = EMF.createEntityManager();
        RennenFahrer rennenFahrer = null;

        try {
            rennenFahrer = em.find(RennenFahrer.class, rennenFahrerId);
            if (rennenFahrer != null) {
                System.out.println("Rennergebnis gefunden:");
                System.out.println("ID: " + rennenFahrer.getRennenFahrerId());
                System.out.println(rennenFahrer.getFahrer());
                System.out.println(rennenFahrer.getRennen());
                System.out.println(rennenFahrer.getStatus());
                System.out.println("Zeit: " + rennenFahrer.getZeit());
            } else {
                System.err.println("Kein Rennergebnis mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return rennenFahrer;
    }

    public static void rennenFahrerUpdaten(RennenFahrer rennenFahrer) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(rennenFahrer);
            et.commit();
            System.out.println("Ergebnisse erfolgreich aktualisiert: " + rennenFahrer);

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void rennenFahrerLoeschen(RennenFahrerId rennenFahrerId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            RennenFahrer rennenFahrer = em.find(RennenFahrer.class, rennenFahrerId);
            if (rennenFahrer == null) {
                System.err.println("Rennergebnis nicht gefunden.");
                et.rollback();
                return;
            }

            em.remove(rennenFahrer);
            et.commit();

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.err.println("Fehler beim Löschen des Rennergebnisses." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }
}
