package at.fhburgenland.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;


public class QueryService {

    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static boolean teamPlatzierung() {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        et = em.getTransaction();
        et.begin();
        try {
            String query = """
                    SELECT t.teamName AS teamName,
                    SUM(CASE WHEN s.statusBeschreibung = 'Erster Platz' THEN 1 ELSE 0 END) AS ersterPlatz,
                    SUM(CASE WHEN s.statusBeschreibung = 'Zweiter Platz' THEN 1 ELSE 0 END) AS zweiterPlatz,
                    SUM(CASE WHEN s.statusBeschreibung = 'Dritter Platz' THEN 1 ELSE 0 END) AS dritterPlatz,
                    SUM(CASE WHEN s.statusBeschreibung = 'Ausgeschieden' THEN 1 ELSE 0 END) AS ausgeschieden
                    FROM Team t
                    JOIN t.fahrzeug fz
                    JOIN fz.fahrer fa
                    JOIN fa.rennenZuordnungen rf
                    JOIN rf.status s
                    GROUP BY t.teamName""";

            List<Object[]> result = em.createQuery(query).getResultList();

            for (Object[] row : result) {
                String teamName = (String) row[0];
                int erster = ((Number) row[1]).intValue();
                int zweiter = ((Number) row[2]).intValue();
                int dritter = ((Number) row[3]).intValue();
                int ausgeschieden = ((Number) row[4]).intValue();
                System.out.printf("%s: 1. Platz: %d, 2.: %d, 3.: %d, DNF: %d%n", teamName, erster, zweiter, dritter, ausgeschieden);
            }
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }

    public static void beteiligteFahrzeugeAusgeben(int rennstreckenId) {
        EntityManager em = EMF.createEntityManager();
        String query = """
                SELECT DISTINCT ft.modell, ft.motor
                    FROM Rennstrecke rs
                    JOIN rs.rennen r
                    JOIN r.fahrerZuordnungen rf
                    JOIN rf.fahrer fa
                    JOIN fa.fahrzeug fz
                    JOIN fz.fahrzeugtyp ft
                    WHERE rs.rennstreckenId = :rennstreckenId""";
        List<Object[]> result = em.createQuery(query)
                .setParameter("rennstreckenId", rennstreckenId)
                .getResultList();

        for (Object[] row : result) {
            String model = (String) row[0];
            String motor = (String) row[1];
            System.out.println("model: " + model + " motor: " + motor);
        }
        em.close();
    }
}