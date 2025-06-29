package at.fhburgenland.ui;

/**
 * Stellt alle Menüanzeigen zur Verfügung
 */
public class Menu {
    private static final String hauptmenu = """
            Herzlich Willkommen zur Rennverwaltung!
            
            Bitte eine Zahl zwischen 1 und 11 auswählen:
            1 - Rennstrecken verwalten
            2 - Rennen verwalten
            3 - Rennergebnisse verwalten
            4 - Fahrer verwalten
            5 - Fahrzeuge verwalten
            6 - Fahrzeugtypen verwalten
            7 - Teams verwalten
            8 - Hauptsponsoren verwalten
            9 - Nationalität verwalten
            10 - Queries
            11 - Rennverwaltung beenden
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

    private static final String rennenFahrerMenu = """
            *** RENNERGEBNISSE ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Rennergebnisse anzeigen
            2 - Neues Rennergebnis anlegen
            3 - Rennergebnis bearbeiten
            4 - Rennergebnis löschen
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

    private static final String fahrzeugtypMenu = """
            *** FAHRZEUGTYPEN ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Fahrzeugtypen anzeigen
            2 - Neuen Fahrzeugtyp anlegen
            3 - Fahrzeugtyp bearbeiten
            4 - Fahrzeugtyp löschen
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
    private static final String hauptsponsorMenu = """
            *** HAUPTSPONSOREN ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Hauptsponsoren anzeigen
            2 - Neuen Hauptsponsor anlegen
            3 - Hauptsponsor bearbeiten
            4 - Hauptsponsor löschen
            5 - Zurück zum Hauptmenü
            """;

    private static final String nationalitaetsMenu = """
            *** NATIONALITÄTEN ***
            
            Bitte eine Zahl zwischen 1 und 5 auswählen:
            1 - Alle Nationalitäten anzeigen
            2 - Neue Nationalität anlegen
            3 - Nationalität bearbeiten
            4 - Nationalität löschen
            5 - Zurück zum Hauptmenü
            """;

    private static final String queriesMenu = """
            *** QUERIES ***
            
            Bitte eine Zahl zwischen 1 und 3 auswählen:
            1 - Auflistung Team-Platzierungen
            2 - Auflistung aller Fahrzeuge nach Rennstrecke
            3 - Zurück zum Hauptmenü
            """;

    /**
     * Display Methoden für die Menüs
     */
    public static void hauptmenuAnzeigen() {
        System.out.println(hauptmenu);
    }

    protected static void zeigeRennstreckenMenu() {
        System.out.println(rennstreckeMenu);
    }

    protected static void zeigeRennenMenu() {
        System.out.println(rennenMenu);
    }

    protected static void zeigeRennenFahrerMenu() {
        System.out.println(rennenFahrerMenu);
    }

    protected static void zeigeFahrerMenu() {
        System.out.println(fahrerMenu);
    }

    protected static void zeigeFahrzeugMenu() {
        System.out.println(fahrzeugMenu);
    }

    protected static void zeigeFahrzeugtypMenu() {
        System.out.println(fahrzeugtypMenu);
    }

    protected static void zeigeTeamMenu() {
        System.out.println(teamMenu);
    }

    protected static void zeigeHauptsponsorMenu() {
        System.out.println(hauptsponsorMenu);
    }

    protected static void zeigeNationalitaetsMenu() {
        System.out.println(nationalitaetsMenu);
    }

    protected static void zeigeQueriesMenu() {
        System.out.println(queriesMenu);
    }
}
