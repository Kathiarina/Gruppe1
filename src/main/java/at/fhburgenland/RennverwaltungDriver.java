package at.fhburgenland;

import at.fhburgenland.ui.HauptsponsorUI;
import at.fhburgenland.ui.Menu;
import at.fhburgenland.ui.RennstreckeUI;

import java.util.Scanner;

public class RennverwaltungDriver {
    Scanner scanner = new Scanner(System.in);
    private final Menu menu = new Menu();
    private final RennstreckeUI rennstreckeUI = new RennstreckeUI(scanner, menu);
    private final HauptsponsorUI hauptsponsorUI = new HauptsponsorUI(scanner, menu);
    private boolean systemRunning = true;

    public void rennverwaltungStart() {
        while (systemRunning) {
            Menu.hauptmenuAnzeigen();
            String userEingabe = scanner.nextLine();
            switch (userEingabe) {
                case "1" -> rennstreckeUI.rennstreckeMenu();
                case "6" -> hauptsponsorUI.hauptsponsorMenu();
                case "8" -> {
                    System.out.println("Auf Wiedersehen!");
                    systemRunning = false;
                }
                default -> System.err.println("Ungültige Eingabe, bitte erneut versuchen.");
            }

        }
    }

}
