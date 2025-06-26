package at.fhburgenland.ui;

import at.fhburgenland.model.Nationalitaet;
import at.fhburgenland.service.NationalitaetService;

import java.util.List;
import java.util.Scanner;

public class NationalitaetsUI {
    private final Scanner scanner;
    private final Menu menu;

    public NationalitaetsUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void nationalitaetsMenu() {
        while (true) {
            menu.zeigeNationalitaetsMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleNationalitaetenAnzeigen();
                    break;
                case "2":
                    createNationalitaet();
                    break;
                case "3":
                    alleNationalitaetenAnzeigen();
                    updateNationalitaet();
                    break;
                case "4":
                    alleNationalitaetenAnzeigen();
                    deleteNationalitaet();
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

    public void createNationalitaet() {
        try {
            Nationalitaet nationalitaet = new Nationalitaet();
            System.out.println("Bitte die Beschreibung der Nationalität eingeben:");
            String nationalitaetsBeschreibung = scanner.nextLine();
            if (nationalitaetsBeschreibung.isEmpty()) {
                System.err.println("Beschreibung der Nationalität darf nicht leer sein.");
                return;
            }

            nationalitaet.setNationalitaetsBeschreibung(nationalitaetsBeschreibung);

            NationalitaetService.nationalitaetHinzufuegen(nationalitaet);
            System.out.println("Nationalität erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Nationalität konnte nicht angelegt werden." + e.getMessage());
        }

    }

    public void updateNationalitaet() {
        try {
            System.out.println("Bitte die ID der zu bearbeitenden Nationalität eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Nationalitäts-ID");
                return;
            }

            Nationalitaet nationalitaet = NationalitaetService.nationalitaetAnzeigenNachId(id);
            if (nationalitaet == null) {
                System.out.println("Nationalität nicht gefunden.");
                return;
            }

            System.out.println("Neue Beschreibung für die Nationalität eingeben (Enter zum überspringen):");
            String neueBeschreibung = scanner.nextLine();
            if (!neueBeschreibung.isBlank()) {
                nationalitaet.setNationalitaetsBeschreibung(neueBeschreibung);
            }

            NationalitaetService.nationalitaetUpdaten(nationalitaet);
        } catch (Exception e) {
            System.err.println("Nationalität konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    public void deleteNationalitaet() {
        try {
            System.out.println("Bitte die ID der zu löschenden Nationalität eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Nationalitäts-ID");
                return;
            }

            Nationalitaet nationalitaet = NationalitaetService.nationalitaetAnzeigenNachId(id);
            if (nationalitaet == null) {
                System.err.println("Nationalität kann nicht gelöscht werden, da sie nicht existiert.");
                return;
            }

            String begruendung = NationalitaetService.pruefeVerknuepfungMitNationalitaet(nationalitaet);
            if (begruendung != null) {
                System.err.println(begruendung);
                return;
            }

            NationalitaetService.nationalitaetLoeschen(id);
            System.out.printf("Nationalität mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Nationalität konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    public void alleNationalitaetenAnzeigen() {
        List<Nationalitaet> nationalitaeten = NationalitaetService.alleNationalitaetenAnzeigen();
        try {
            if (nationalitaeten != null && !nationalitaeten.isEmpty()) {
                for (Nationalitaet nationalitaet : nationalitaeten) {
                    System.out.println("Nationalität Nr: " + nationalitaet.getNationalitaetsId() + ", " + nationalitaet.getNationalitaetsBeschreibung());
                }
            } else {
                System.err.println("Keine Nationalität gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Nationalitäten." + e.getMessage());
            e.printStackTrace();
        }
    }
}
