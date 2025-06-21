package at.fhburgenland;

import tables.Rennstrecke;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Rennverwaltung_Manager {

    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");
    // TODO Fields of Rennverwaltung_Manager

    public Rennverwaltung_Manager() {
        // TODO Initialization of fields of Rennverwaltung_Manager
    }

    public static void addRennstrecke(String ort, String bundesland) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Rennstrecke rennstrecke = new Rennstrecke(ort, bundesland);
            em.persist(rennstrecke);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            em.close();

        }
    }


}
