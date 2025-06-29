package at.fhburgenland.ui;

import at.fhburgenland.model.Hauptsponsor;
import at.fhburgenland.service.HauptsponsorService;

import java.util.List;
import java.util.Scanner;

/**
 * Benutzeroberfläche zur Verwaltung von Hauptsponsoren
 * Bietet Funktionen zur Anzeige, Erstellung, Bearbeitung und Löschung von Hauptsponsoren
 */
public class HauptsponsorUI {

    private final Scanner scanner;

    public HauptsponsorUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
    }

    /**
     * Startet das Hauptsponsormenü und listet Optionen für User
     */
    public void hauptsponsorMenu() {
        while (true) {
            Menu.zeigeHauptsponsorMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleHauptsponsorenAnzeigen();
                    break;
                case "2":
                    createHauptsponsor();
                    break;
                case "3":
                    alleHauptsponsorenAnzeigen();
                    updateHauptsponsor();
                    break;
                case "4":
                    alleHauptsponsorenAnzeigen();
                    deleteHauptsponsor();
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
     * Erstellt einen neuen Hauptsponsor
     */
    public void createHauptsponsor() {
        try {
            Hauptsponsor hauptsponsor = new Hauptsponsor();
            System.out.println("Bitte den Namen des Hauptsponsors eingeben:");
            String hauptsponsorName = scanner.nextLine();
            if (hauptsponsorName.isEmpty()) {
                System.err.println("Hauptsponsorname darf nicht leer sein.");
                return;
            }
            hauptsponsor.setHauptsponsorName(hauptsponsorName);
            System.out.println("Bitte die jährliche Sponsorsumme des Hauptsponsors eingeben (Ganze Zahl):");
            String sponsorsummeEingabe = scanner.nextLine();
            if (!sponsorsummeEingabe.isBlank()) {
                try {
                    int neueJaehrlicheSponsorsumme = Integer.parseInt(sponsorsummeEingabe);
                    hauptsponsor.setJaehrlicheSponsorsumme(neueJaehrlicheSponsorsumme);
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige jährliche Sponsorsumme. Bitte geben Sie eine Ganzzahl ein.");
                    return;
                }
            }
            HauptsponsorService.hauptsponsorHinzufuegen(hauptsponsor);
            System.out.println("Hauptsponsor erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Hauptsponsor konnte nicht angelegt werden." + e.getMessage());
        }
    }

    /**
     * Aktualisiert die Daten eines bereits existierenden Hauptsponsors
     */
    public void updateHauptsponsor() {
        try {
            System.out.println("Bitte die ID des zu bearbeitenden Hauptsponsors eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Hauptsponsor-ID");
                return;
            }

            Hauptsponsor hauptsponsor = HauptsponsorService.hauptsponsorAnzeigenNachId(id);
            if (hauptsponsor == null) {
                System.err.println("Hauptsponsor kann nicht bearbeitet werden, da er nicht existiert.");
                return;
            }

            System.out.println("Neuen Hauptsponsornamen eingeben (Enter zum überspringen):");
            String neuerHauptsponsorname = scanner.nextLine();
            if (!neuerHauptsponsorname.isBlank()) {
                hauptsponsor.setHauptsponsorName(neuerHauptsponsorname);
            }
            System.out.println("Neue jährliche Sponsorsumme eingeben (Enter zum überspringen):");
            String sponsorsummeEingabe = scanner.nextLine();
            if (!sponsorsummeEingabe.isBlank()) {
                try {
                    int neueJaehrlicheSponsorsumme = Integer.parseInt(sponsorsummeEingabe);
                    if (neueJaehrlicheSponsorsumme <= 0) {
                        System.out.println("Sponsorsumme muss positiv sein.");
                        return;
                    }
                    hauptsponsor.setJaehrlicheSponsorsumme(neueJaehrlicheSponsorsumme);
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige jährliche Sponsorsumme. Bitte geben Sie eine Ganzzahl ein.");
                    return;
                }
            }
            HauptsponsorService.hauptsponsorUpdaten(hauptsponsor);
        } catch (Exception e) {
            System.err.println("Hauptsponsor konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    /**
     * Löscht einen Hauptsponsor, wenn keine Verknüpfungen bestehen
     */
    public void deleteHauptsponsor() {
        try {
            System.out.println("Bitte die ID des zu löschenden Hauptsponsors eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Hauptsponsor-ID");
                return;
            }

            Hauptsponsor hauptsponsor = HauptsponsorService.hauptsponsorAnzeigenNachId(id);
            if (hauptsponsor == null) {
                System.err.println("Hauptsponsor kann nicht gelöscht werden, da er nicht existiert.");
                return;
            }

            if (hauptsponsor.getTeam() != null) {
                System.err.println("Hauptsponsor ist einem Team zugeordnet und kann daher nicht gelöscht werden.");
                return;
            }
            HauptsponsorService.hauptsponsorLoeschen(id);
            System.out.printf("Hauptsponsor mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Hauptsponsor konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    /**
     * Zeigt alle vorhandenen Hauptsponsoren an
     */
    private void alleHauptsponsorenAnzeigen() {
        List<Hauptsponsor> hauptsponsoren = HauptsponsorService.alleHauptsponsorenAnzeigen();
        try {
            if (hauptsponsoren != null && !hauptsponsoren.isEmpty()) {
                for (Hauptsponsor hauptsponsor : hauptsponsoren) {
                    System.out.println("Hauptsponsor Nr: " + hauptsponsor.getHauptsponsorId() + ", Hauptsponsorname " + hauptsponsor.getHauptsponsorName() + ", Jährliche Sponsorsumme " + hauptsponsor.getJaehrlicheSponsorsumme() + "€");
                }
            } else {
                System.err.println("Keine Hauptsponsoren gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Hauptsponsoren." + e.getMessage());
        }
    }
}
