package at.fhburgenland.service;

import at.fhburgenland.model.Rennen;
import jakarta.persistence.*;

import java.util.List;

public class RennenService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static void rennenHinzufuegen(Rennen rennen) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            System.out.println("Neues Rennen wurde angelegt: " + rennen);
            em.persist(rennen);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("Fehler beim Speichern des Rennens: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static List<Rennen> alleRennenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT r FROM Rennen r";
        TypedQuery<Rennen> tq = em.createQuery(query, Rennen.class);

        List<Rennen> rennenListe = null;

        try {
            rennenListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return rennenListe;
    }

    public static Rennen rennenAnzeigenNachId(int rennenId) {
        EntityManager em = EMF.createEntityManager();
        Rennen rennen = null;

        try {
            rennen = em.find(Rennen.class, rennenId);
            if (rennen != null) {
                System.out.println("Rennen gefunden:");
                System.out.println("ID: " + rennen.getRennenId());
                System.out.println("Datum und Uhrzeit: " + rennen.getDatumUhrzeit());
                System.out.println("Rennstrecke: " + rennen.getRennstrecke());
            } else {
                System.err.println("Kein Rennen mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return rennen;
    }

    public static void rennenUpdaten(Rennen rennen) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            em.merge(rennen);
            et.commit();
            System.out.println("Rennen erfolgreich aktualisiert: " + rennen);

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void rennenLoeschen(int rennenId) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            Rennen rennen = em.find(Rennen.class, rennenId);
            if (rennen == null) {
                System.err.println("Rennen nicht gefunden.");
                et.rollback();
                return;
            }
            String begruendung = pruefeVerknuepfungMitRennen(rennen);
            if (begruendung != null) {
                System.err.println(begruendung);
                et.rollback();
                return;
            }

            em.remove(rennen);
            et.commit();

        } catch (Exception e) {
            if (et != null) {
                et.rollback();
                System.out.println("Fehler beim Löschen des Rennens.");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }

    public static String pruefeVerknuepfungMitRennen(Rennen rennen) {
        boolean verlaeuftAufRennstrecke = rennen.getRennstrecke() != null;
        boolean hatFahrer = rennen.getDatumUhrzeit() != null;

        if (verlaeuftAufRennstrecke || hatFahrer) {
            StringBuilder grund = new StringBuilder("Rennen kann nicht gelöscht werden, da es ");
            if (verlaeuftAufRennstrecke && hatFahrer) {
                grund.append("einer Rennstrecke und einem Fahrer");
            } else if (verlaeuftAufRennstrecke) {
                grund.append("einer Rennstrecke ");
            } else {
                grund.append("einem Fahrer ");
            }
            grund.append("zugeordnet ist.");
            return grund.toString();
        }
        return null;
    }
}
