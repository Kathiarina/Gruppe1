package at.fhburgenland.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class QueryService {

    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static boolean teamPlatzierung() {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        et = em.getTransaction();
        et.begin();
        try {
            String sql = """
                    SELECT t.teamName AS teamName,
                    SUM(CASE WHEN s.statusBeschreibung = 'Erster Platz' THEN 1 ELSE 0 END) AS ersterPlatz,
                    SUM(CASE WHEN s.statusBeschreibung = 'Zweiter Platz' THEN 1 ELSE 0 END) AS zweiterPlatz,
                    SUM(CASE WHEN s.statusBeschreibung = 'Dritter Platz' THEN 1 ELSE 0 END) AS dritterPlatz,
                    SUM(CASE WHEN s.statusBeschreibung = 'Ausgeschieden' THEN 1 ELSE 0 END) AS ausgeschieden
                    FROM Team t
                    JOIN Fahrzeug f ON t.teamId = f.teamId
                    JOIN Fahrer fa ON f.fahrzeugId = fa.fahrzeugId
                    JOIN RennenFahrer rf ON fa.fahrerId = rf.fahrerId
                    JOIN Status s ON s.statusId = rf.statusId
                    GROUP BY t.teamName""";

            List<Object[]> result = em.createQuery(sql).getResultList();

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

    public static void beteiligteFahrzeugeAusgeben(int rennenId) {
        EntityManager em = EMF.createEntityManager();
        String queryRennenstreckenFilter = """
                SELECT DISTINCT ft.modell, ft.motor
                    FROM Rennstrecke rs
                    JOIN Rennen r ON rs.rennstreckenId = r.rennstreckenId
                    JOIN Rennen_Fahrer rf ON r.rennenId = rf.rennenId
                    JOIN Fahrer f ON rf.fahrerId = f.fahrerId
                    JOIN Fahrzeug fahrz ON f.fahrzeugId = fahrz.fahrzeugId
                    JOIN Fahrzeugtyp ft ON fahrz.fahrzeugtypId = ft.fahrzeugtypId
                    WHERE rs.rennstreckenId = 1;""";
        List<Object[]> result = em.createNativeQuery(queryRennstreckenFilter).getResultList();

        for (Object[] row : result) {
            String model = (String) row[0];
            String motor = (String) row[1];
        }
        em.close();
    }
}