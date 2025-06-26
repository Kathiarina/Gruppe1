package at.fhburgenland;

import at.fhburgenland.ui.*;

import java.util.Scanner;

public class RennverwaltungDriver {
    Scanner scanner = new Scanner(System.in);
    private final Menu menu = new Menu();
    private final RennstreckeUI rennstreckeUI = new RennstreckeUI(scanner, menu);
    private final HauptsponsorUI hauptsponsorUI = new HauptsponsorUI(scanner, menu);
    private final FahrerUI fahrerUI = new FahrerUI(scanner, menu);
    private final FahrzeugtypUI fahrzeugtypUI = new FahrzeugtypUI(scanner, menu);
    private final NationalitaetsUI nationalitaetsUI = new NationalitaetsUI(scanner, menu);
    private final TeamUI teamUI = new TeamUI(scanner, menu);
    private final FahrzeugUI fahrzeugUI = new FahrzeugUI(scanner, menu);
    private boolean systemRunning = true;

    public void rennverwaltungStart() {
        while (systemRunning) {
            Menu.hauptmenuAnzeigen();
            String userEingabe = scanner.nextLine();
            switch (userEingabe) {
                case "1" -> rennstreckeUI.rennstreckeMenu();
                case "3" -> fahrerUI.fahrerMenu();
                case "4" -> fahrzeugUI.fahrzeugMenu();
                case "5" -> fahrzeugtypUI.fahrzeugtypMenu();
                case "6" -> teamUI.teamMenu();
                case "7" -> hauptsponsorUI.hauptsponsorMenu();
                case "8" -> nationalitaetsUI.nationalitaetsMenu();
                case "10" -> {
                    System.out.println("Auf Wiedersehen!");
                    systemRunning = false;
                }
                default -> System.err.println("Ungültige Eingabe, bitte erneut versuchen.");
            }
        }
    }
}
