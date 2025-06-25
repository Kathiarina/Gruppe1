package at.fhburgenland.ui;

import at.fhburgenland.model.Rennstrecke;
import at.fhburgenland.service.RennstreckeService;

import java.util.List;
import java.util.Scanner;

public class RennstreckeUI {
    private final Scanner scanner;
    private final Menu menu;

    public RennstreckeUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void rennstreckeMenu() {
        while (true) {
            menu.zeigeRennstreckenMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    List<Rennstrecke> rennstrecken = RennstreckeService.alleRennstreckenAnzeigen();
                    if (rennstrecken.isEmpty()) {
                        System.err.println("Es wurde keine Rennstrecke gefunden.");
                    }
                    break;
                case "2":
                    createRennstrecke();
                    break;
                case "3":
                    RennstreckeService.alleRennstreckenAnzeigen();
                    updateRennstrecke();
                    break;
                case "4":
                    RennstreckeService.alleRennstreckenAnzeigen();
                    deleteRennstrecke();
                    break;
                case "5":
                    System.out.println("Zurück zum Hauptmenü");
                    return;
                default:
                    System.err.println("Ungültige Eingabe, bitte nocheinmal versuchen.");
                    break;
            }
        }
    }

    public void createRennstrecke() {
        try {
            Rennstrecke rennstrecke = new Rennstrecke();
            System.out.println("Bitte den Ort der Rennstrecke eingeben:");
            String ort = scanner.nextLine();
            if(ort.isEmpty()) {
                System.err.println("Ort darf nicht leer sein.");
                return;
            }
            rennstrecke.setOrt(ort);
            System.out.println("Bitte das Bundesland der Rennstrecke eingeben:");
            String bundesland = scanner.nextLine();
            if(bundesland.isEmpty()) {
                System.err.println("Bundesland darf nicht leer sein.");
                return;
            }
            rennstrecke.setBundesland(bundesland);
            RennstreckeService.rennstreckeHinzufuegen(rennstrecke);
            System.out.println("Rennstrecke erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Rennstrecke konnte nicht angelegt werden." + e.getMessage());
        }
    }

    public void updateRennstrecke() {
        try {
            System.out.println("Bitte die ID der zu bearbeitenden Rennstrecke eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Rennstrecken-ID");
                return;
            }

            Rennstrecke rennstrecke = RennstreckeService.rennstreckeAnzeigenNachId(id);
            if (rennstrecke == null) {
                System.out.println("Rennstrecke nicht gefunden.");
                return;
            }

            System.out.println("Neuen Ort eingeben:");
            String neuerOrt = scanner.nextLine();
            if(!neuerOrt.isBlank()){
                rennstrecke.setOrt(neuerOrt);
            }
            System.out.println("Neues Bundesland eingeben:");
            String neuesBundesland = scanner.nextLine();
            if(!neuesBundesland.isBlank()){
                rennstrecke.setBundesland(neuesBundesland);
            }
            RennstreckeService.rennstreckeUpdaten(rennstrecke);
        } catch (Exception e) {
            System.err.println("Rennstrecke konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    public void deleteRennstrecke() {
        try {
            System.out.println("Bitte die ID der zu löschenden Rennstrecke eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Rennstrecken-ID");
                return;
            }

            Rennstrecke rennstrecke = RennstreckeService.rennstreckeAnzeigenNachId(id);
            if (rennstrecke == null) {
                System.err.println("Rennstrecke nicht gefunden.");
                return;
            }
            RennstreckeService.rennstreckeLoeschen(id);
            System.out.printf("Rennstrecke mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Rennstrecke konnte nicht gelöscht werden." + e.getMessage());
        }
    }
}
