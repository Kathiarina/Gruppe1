package at.fhburgenland.service;

import at.fhburgenland.model.Fahrzeugtyp;
import at.fhburgenland.model.Nationalitaet;
import jakarta.persistence.*;

import java.util.List;

public class NationalitaetService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static void nationalitaetHinzufuegen(Nationalitaet nationalitaet) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            System.out.println("Neue Nationalität wurde angelegt: " + nationalitaet);
            em.persist(nationalitaet);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern der Nationalität: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static List<Nationalitaet> alleNationalitaetenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT n FROM Nationalitaet n";
        TypedQuery<Nationalitaet> tq = em.createQuery(query, Nationalitaet.class);

        List<Nationalitaet> nationalitaetenListe = null;

        try {
            nationalitaetenListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return nationalitaetenListe;
    }

    public static Nationalitaet nationalitaetAnzeigenNachId(int nationalitaetId) {
        EntityManager em = EMF.createEntityManager();
        Nationalitaet nationalitaet = null;

        try {
            nationalitaet = em.find(Nationalitaet.class, nationalitaetId);
            if (nationalitaet != null) {
                System.out.println("Nationalität gefunden:");
                System.out.println("ID: " + nationalitaet.getNationalitaetsId());
                System.out.println("Nationalität: " + nationalitaet.getNationalitaetsBeschreibung());
            } else {
                System.err.println("Keine Nationalität mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return nationalitaet;
    }

    public static void nationalitaetUpdaten(Nationalitaet nationalitaet) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(nationalitaet);
            et.commit();
            System.out.println("Nationalität erfolgreich aktualisiert: " + nationalitaet);

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void nationalitaetLoeschen(int nationalitaetsId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Nationalitaet nationalitaet = em.find(Nationalitaet.class, nationalitaetsId);
            if (nationalitaet == null) {
                System.err.println("Nationalität nicht gefunden.");
                et.rollback();
                return;
            }
            String begruendung = pruefeVerknuepfungMitNationalitaet(nationalitaet);
            if (begruendung != null) {
                System.err.println(begruendung);
                et.rollback();
                return;
            }

            em.remove(nationalitaet);
            et.commit();

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen der Nationalität.");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }

    public static String pruefeVerknuepfungMitNationalitaet(Nationalitaet nationalitaet) {
        boolean hatFahrer = nationalitaet.getFahrer() != null && !nationalitaet.getFahrer().isEmpty();
        boolean hatTeam = nationalitaet.getTeam() != null && !nationalitaet.getTeam().isEmpty();

        if (hatFahrer || hatTeam) {
            StringBuilder grund = new StringBuilder("Nationalität kann nicht gelöscht werden, da sie ");
            if (hatFahrer && hatTeam) {
                grund.append("einem Fahrer und einem Team ");
            } else if (hatFahrer) {
                grund.append("einem Fahrer ");
            } else {
                grund.append("einem Team ");
            }
            grund.append("zugeordnet ist.");
            return grund.toString();
        }
        return null;
    }
}
