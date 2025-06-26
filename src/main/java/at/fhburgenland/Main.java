package at.fhburgenland;

public class Main {


    public static void main(String[] args) {
        try {
            RennverwaltungDriver rennverwaltung = new RennverwaltungDriver();
            rennverwaltung.rennverwaltungStart();
        } catch (Exception e) {
            System.err.println("Fehler beim Starten der Rennverwaltung" + e.getMessage());
            e.printStackTrace();
        }
    }
}



