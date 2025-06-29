package at.fhburgenland.service;

import at.fhburgenland.model.Rennen;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Rennen-Entitäten
 * Beinhaltet CRUD-Methoden und Überprüfung von Verknüpfungen
 */
public class RennenService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Speichert ein neues Rennen in der Datenbank
     *
     * @param rennen Rennen, das gespeichert wird
     */
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
        } finally {
            em.close();
        }
    }

    /**
     * Gibt alle Rennen aus der Datenbank zurück
     *
     * @return Liste aller Rennen
     */
    public static List<Rennen> alleRennenAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT r FROM Rennen r";
        TypedQuery<Rennen> tq = em.createQuery(query, Rennen.class);

        List<Rennen> rennenListe = null;

        try {
            rennenListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return rennenListe;
    }

    /**
     * Sucht ein Rennen anhand der ID und gibt es zurück und seine Informationen in der Konsole aus
     *
     * @param rennenId ID des Rennens
     * @return Gefundenes Rennen oder null
     */
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
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return rennen;
    }

    /**
     * Aktualisiert ein Rennen in der Datenbank
     *
     * @param rennen Rennen mit aktualisierten Werten
     */
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
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Löscht ein Rennen anhand der ID, wenn keine Verknüpfungen bestehen
     *
     * @param rennenId ID des Rennens, das gelöscht wird
     */
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
                System.out.println("Fehler beim Löschen des Rennens." + e.getMessage());
            }
        } finally {
            em.close();
        }
    }

    /**
     * Prüft, ob ein Rennen mit anderen Entitäten verknüpft ist
     * Wird verwendet, um das Löschen abzusichern
     *
     * @param rennen Rennen, das überprüft wird
     * @return Rückgabe einer Begründung als String oder null, wenn keine Verknüpfung besteht
     */
    public static String pruefeVerknuepfungMitRennen(Rennen rennen) {
        if (rennen == null) {
            return "Rennen ist null. Prüfung nicht möglich.";
        }

        try {
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
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
