package at.fhburgenland.ui;

public class Menu {
    private static final String hauptmenu = """
            Herzlich Willkommen zur Rennverwaltung!
            
            Bitte eine Zahl zwischen 1 und 8 auswählen:
            1 - Rennstrecken verwalten
            2 - Rennen verwalten
            3 - Fahrer verwalten
            4 - Fahrzeuge verwalten
            5 - Teams verwalten
            6 - Sponsoren verwalten
            7 - Abfragen
            8 - Rennverwaltung beenden
            """;

    private static final String rennstreckeMenu = """
            *** RENNSTRECKEN ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Rennstrecken anzeigen
            2 - Neue Rennstrecke anlegen
            3 - Rennstrecke bearbeiten
            4 - Rennstrecke löschen
            5 - Zurück zum Hauptmenü
            """;

    private static final String rennenMenu = """
            *** RENNEN ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Rennen anzeigen
            2 - Neues Rennen anlegen
            3 - Rennen bearbeiten
            4 - Rennen löschen
            5 - Zurück zum Hauptmenü
            """;
    private static final String fahrerMenu = """
            *** FAHRER ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Fahrer anzeigen
            2 - Neuen Fahrer anlegen
            3 - Fahrer bearbeiten
            4 - Fahrer löschen
            5 - Zurück zum Hauptmenü
            """;
    private static final String fahrzeugMenu = """
            *** FAHRZEUGE ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Fahrzeuge anzeigen
            2 - Neues Fahrzeug anlegen
            3 - Fahrzeug bearbeiten
            4 - Fahrzeug löschen
            5 - Zurück zum Hauptmenü
            """;
    private static final String teamMenu = """
            *** TEAMS ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Teams anzeigen
            2 - Neues Team anlegen
            3 - Team bearbeiten
            4 - Team löschen
            5 - Zurück zum Hauptmenü
            """;
    private static final String sponsorMenu = """
            *** SPONSOREN ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Sponsoren anzeigen
            2 - Neuen Sponsor anlegen
            3 - Sponsor bearbeiten
            4 - Sponsor löschen
            5 - Zurück zum Hauptmenü
            """;

    public static void hauptmenuAnzeigen() {
        System.out.println(hauptmenu);
    }

    protected static void zeigeRennstreckenMenu() {
        System.out.println(rennstreckeMenu);
    }

    protected static void zeigeRennenMenu() {
        System.out.println(rennenMenu);
    }

    protected static void zeigeFahrerMenu() {
        System.out.println(fahrerMenu);
    }

    protected static void zeigeFahrzeugMenu() {
        System.out.println(fahrzeugMenu);
    }

    protected static void zeigeTeamMenu() {
        System.out.println(teamMenu);
    }

    protected static void zeigeSponsorMenu() {
        System.out.println(sponsorMenu);
    }
}
