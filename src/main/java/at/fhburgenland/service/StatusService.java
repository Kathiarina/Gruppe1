package at.fhburgenland.service;

import at.fhburgenland.model.Status;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Status-Entitäten
 * Beinhaltet Read-Methoden, da der Status nicht erstellt, verändert oder gelöscht werden darf
 */
public class StatusService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    /**
     * Gibt alle Status aus der Datenbank zurück
     * @return Liste aller Status
     */
    public static List<Status> alleStatusAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT s FROM Status s";
        TypedQuery<Status> tq = em.createQuery(query, Status.class);

        List<Status> statusListe = null;

        try {
            statusListe = tq.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return statusListe;
    }

    /**
     * Sucht einen Status anhand der ID und gibt ihn zurück und seine Informationen in der Konsole aus
     * @param statusId ID des Status
     * @return Gefundener Status oder null
     */
    public static Status statusAnzeigenNachId(int statusId) {
        EntityManager em = EMF.createEntityManager();
        Status status = null;

        try {
            status = em.find(Status.class, statusId);
            if (status != null) {
                System.out.println("Status gefunden:");
                System.out.println("ID: " + status.getStatusId());
                System.out.println("Status: " + status.getStatusBeschreibung());
            } else {
                System.err.println("Kein Status mit dieser ID gefunden.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return status;
    }
}
