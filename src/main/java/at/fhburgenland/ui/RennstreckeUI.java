package at.fhburgenland.ui;

import at.fhburgenland.model.Rennstrecke;
import at.fhburgenland.service.RennstreckeService;

import java.util.List;
import java.util.Scanner;

/**
 * Benutzeroberfläche zur Verwaltung von Rennstrecken
 * Bietet Funktionen zur Anzeige, Erstellung, Bearbeitung und Löschung von Rennstrecken
 */
public class RennstreckeUI {
    private final Scanner scanner;

    public RennstreckeUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
    }

    /**
     * Startet das Rennstreckenmenü und listet Optionen für User
     */
    public void rennstreckeMenu() {
        while (true) {
            Menu.zeigeRennstreckenMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleRennstreckenAnzeigen();
                    break;
                case "2":
                    createRennstrecke();
                    break;
                case "3":
                    alleRennstreckenAnzeigen();
                    updateRennstrecke();
                    break;
                case "4":
                    alleRennstreckenAnzeigen();
                    deleteRennstrecke();
                    break;
                case "5":
                    System.out.println("Zurück zum Hauptmenü");
                    return;
                default:
                    System.err.println("Ungültige Eingabe.");
                    break;
            }
        }
    }

    /**
     * Erstellt eine neue Rennstrecke
     */
    public void createRennstrecke() {
        try {
            Rennstrecke rennstrecke = new Rennstrecke();
            System.out.println("Bitte den Ort der Rennstrecke eingeben:");
            String ort = scanner.nextLine();
            if (ort.isEmpty()) {
                System.err.println("Ort darf nicht leer sein.");
                return;
            }
            rennstrecke.setOrt(ort);
            System.out.println("Bitte das Bundesland der Rennstrecke eingeben:");
            String bundesland = scanner.nextLine();
            if (bundesland.isEmpty()) {
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

    /**
     * Aktualisiert die Daten einer bereits existierenden Rennstrecke
     */
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

            System.out.println("Neuen Ort eingeben (Enter zum überspringen):");
            String neuerOrt = scanner.nextLine();
            if (!neuerOrt.isBlank()) {
                rennstrecke.setOrt(neuerOrt);
            }
            System.out.println("Neues Bundesland eingeben (Enter zum überspringen):");
            String neuesBundesland = scanner.nextLine();
            if (!neuesBundesland.isBlank()) {
                rennstrecke.setBundesland(neuesBundesland);
            }
            RennstreckeService.rennstreckeUpdaten(rennstrecke);
        } catch (Exception e) {
            System.err.println("Rennstrecke konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    /**
     * Löscht eine Rennstrecke, wenn keine Verknüpfungen bestehen
     */
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
                System.err.println("Rennstrecke kann nicht gelöscht werden, da sie nicht existiert.");
                return;
            }

            if (!rennstrecke.getRennen().isEmpty()) {
                System.err.println("Rennstrecke ist einem Rennen zugeordnet und kann daher nicht gelöscht werden.");
                return;
            }

            RennstreckeService.rennstreckeLoeschen(id);
            System.out.printf("Rennstrecke mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Rennstrecke konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    /**
     * Zeigt alle vorhandenen Rennstrecken an
     */
    private void alleRennstreckenAnzeigen() {
        List<Rennstrecke> rennstrecken = RennstreckeService.alleRennstreckenAnzeigen();
        try {
            if (rennstrecken != null && !rennstrecken.isEmpty()) {
                for (Rennstrecke rennstrecke : rennstrecken) {
                    System.out.println("Rennstrecke Nr: " + rennstrecke.getRennstreckenId() + ", Ort " + rennstrecke.getOrt() + ", Bundesland " + rennstrecke.getBundesland());
                }
            } else {
                System.err.println("Keine Rennstrecken gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Rennstrecken." + e.getMessage());
        }
    }
}
