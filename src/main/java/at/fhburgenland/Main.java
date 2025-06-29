package at.fhburgenland;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        // Unterdrückt Hibernate-Logausgaben in der Konsole
        disableHibernateInfoLogs();
        try {
            RennverwaltungDriver rennverwaltung = new RennverwaltungDriver();
            rennverwaltung.rennverwaltungStart();
        } catch (Exception e) {
            System.err.println("Fehler beim Starten der Rennverwaltung" + e.getMessage());
        }
    }

    /**
     * Deaktiviert die Ausgaben des Hibernate-Loggers auf der Konsole
     */
    public static void disableHibernateInfoLogs() {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }
}



