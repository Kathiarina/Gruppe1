package at.fhburgenland.service;

import at.fhburgenland.model.Status;
import jakarta.persistence.*;

import java.util.List;

public class StatusService {
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static List<Status> alleStatusAnzeigen() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT hs FROM Hauptsponsor hs";
        TypedQuery<Status> tq = em.createQuery(query, Status.class);

        List<Status> statusListe = null;

        try {
            statusListe = tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return statusListe;
    }

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
            e.printStackTrace();
        } finally {
            em.close();
        }
        return status;
    }
}
