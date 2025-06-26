package at.fhburgenland.ui;

import at.fhburgenland.model.Fahrer;
import at.fhburgenland.model.Fahrzeug;
import at.fhburgenland.model.Nationalitaet;
import at.fhburgenland.service.FahrerService;
import at.fhburgenland.service.FahrzeugService;
import at.fhburgenland.service.NationalitaetService;

import java.util.List;
import java.util.Scanner;

public class FahrerUI {
    private final Scanner scanner;
    private final Menu menu;

    public FahrerUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void fahrerMenu() {
        while (true) {
            menu.zeigeFahrerMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleFahrerAnzeigen();
                    break;
                case "2":
                    createFahrer();
                    break;
                case "3":
                    alleFahrerAnzeigen();
                    updateFahrer();
                    break;
                case "4":
                    alleFahrerAnzeigen();
                    deleteFahrer();
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

    public void createFahrer() {
        try {
            Fahrer fahrer = new Fahrer();
            System.out.println("Bitte den Vornamen des Fahrers eingeben:");
            String vorname = scanner.nextLine();
            if (vorname.isEmpty()) {
                System.err.println("Vorname darf nicht leer sein.");
                return;
            }
            fahrer.setVorname(vorname);
            System.out.println("Bitte den Nachnamen des Fahrers eingeben:");
            String nachname = scanner.nextLine();
            if (nachname.isEmpty()) {
                System.err.println("Nachname darf nicht leer sein.");
                return;
            }
            fahrer.setNachname(nachname);

            Nationalitaet nationalitaet = nationalitaetAuswaehlen();
            if (nationalitaet == null) {
                return;
            }
            fahrer.setNationalitaet(nationalitaet);

            Fahrzeug fahrzeug = fahrzeugAuswaehlen();
            if (fahrzeug == null) {
                return;
            }
            if (FahrerService.fahrzeugBereitsVergeben(fahrzeug, -1)) {
                System.err.println("Dieses Fahrzeug ist bereits einem anderen Fahrer zugeordnet.");
                return;
            }
            fahrer.setFahrzeug(fahrzeug);

            FahrerService.fahrerHinzufuegen(fahrer);
            System.out.println("Fahrer erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Fahrer konnte nicht angelegt werden." + e.getMessage());
        }
    }

    public void updateFahrer() {
        try {
            System.out.println("Bitte die ID des zu bearbeitenden Fahrers eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Fahrer-ID");
                return;
            }

            Fahrer fahrer = FahrerService.fahrerAnzeigenNachId(id);
            if (fahrer == null) {
                System.out.println("Fahrer nicht gefunden.");
                return;
            }

            System.out.println("Neuen Vornamen eingeben (Enter zum überspringen):");
            String neuerVorname = scanner.nextLine();
            if (!neuerVorname.isBlank()) {
                fahrer.setVorname(neuerVorname);
            }
            System.out.println("Neuen Nachnamen eingeben (Enter zum überspringen):");
            String neuerNachname = scanner.nextLine();
            if (!neuerNachname.isBlank()) {
                fahrer.setNachname(neuerNachname);
            }

            System.out.println("Möchten Sie die Nationalität ändern? (j/n):");
            String nationalitaetAendern = scanner.nextLine().toLowerCase();
            if (nationalitaetAendern.equals("j")) {
                Nationalitaet neueNationalitaet = nationalitaetAuswaehlen();
                if (neueNationalitaet != null) {
                    fahrer.setNationalitaet(neueNationalitaet);
                }
            }

            System.out.println("Möchten Sie das Fahrzeug ändern? (j/n):");
            String fahrzeugAendern = scanner.nextLine().toLowerCase();
            if (fahrzeugAendern.equals("j")) {
                Fahrzeug neuesFahrzeug = fahrzeugAuswaehlen();
                if (neuesFahrzeug != null) {
                    if (FahrerService.fahrzeugBereitsVergeben(neuesFahrzeug, fahrer.getFahrerId())) {
                        System.err.println("Dieses Fahrzeug ist bereits vergeben.");
                        return;
                    }
                    fahrer.setFahrzeug(neuesFahrzeug);
                }
            }

            FahrerService.fahrerUpdaten(fahrer);
        } catch (Exception e) {
            System.err.println("Fahrer konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    public void deleteFahrer() {
        try {
            System.out.println("Bitte die ID des zu löschenden Fahrers eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Fahrer-ID");
                return;
            }

            Fahrer fahrer = FahrerService.fahrerAnzeigenNachId(id);
            if (fahrer == null) {
                System.err.println("Fahrer kann nicht gelöscht werden, da er nicht existiert.");
                return;
            }

            String begruendung = FahrerService.pruefeVerknuepfungMitFahrer(fahrer);
            if (begruendung != null) {
                System.err.println(begruendung);
                return;
            }

            FahrerService.fahrerLoeschen(id);
            System.out.printf("Fahrer mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Fahrer konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    private Fahrzeug fahrzeugAuswaehlen() {
        List<Fahrzeug> fahrzeuge = FahrzeugService.alleFahrzeugeAnzeigen();
        if (fahrzeuge == null || fahrzeuge.isEmpty()) {
            System.err.println("Es sind keine Fahrzeuge vorhanden. Bitte zuerst ein Fahrzeug anlegen.");
            return null;
        }
        System.out.println("Verfügbare Fahrzeuge:");
        for (Fahrzeug fahrzeug : fahrzeuge) {
            System.out.println("Fahrzeug Nr: " + fahrzeug.getFahrzeugId() + ", " + fahrzeug.getFahrzeugtyp() + ", " + fahrzeug.getTeam());
        }
        System.out.println("Bitte Fahrzeug-ID auswählen:");
        int fahrzeugId;
        try {
            fahrzeugId = Integer.parseInt(scanner.nextLine());
            Fahrzeug fahrzeug = FahrzeugService.fahrzeugAnzeigenNachId(fahrzeugId);
            if (fahrzeug != null) {
                return fahrzeug;
            } else {
                System.err.println("Fahrzeug mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Fahrzeug-ID");
        }
        return null;
    }

    private Nationalitaet nationalitaetAuswaehlen() {
        List<Nationalitaet> nationalitaeten = NationalitaetService.alleNationalitaetenAnzeigen();
        if (nationalitaeten.isEmpty()) {
            System.err.println("Es sind keine Nationalitäten vorhanden. Bitte zuerst eine Nationalität anlegen.");
            return null;
        }
        System.out.println("Verfügbare Nationalitäten:");
        for (Nationalitaet nationalitaet : nationalitaeten) {
            System.out.println("Nationalität Nr: " + nationalitaet.getNationalitaetsId() + ", " + nationalitaet.getNationalitaetsBeschreibung());
        }
        System.out.println("Bitte Nationalitäts-ID auswählen:");
        int nationalitaetsId;
        try {
            nationalitaetsId = Integer.parseInt(scanner.nextLine());
            Nationalitaet nationalitaet = NationalitaetService.nationalitaetAnzeigenNachId(nationalitaetsId);
            if (nationalitaet != null) {
                return nationalitaet;
            } else {
                System.err.println("Nationalität mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Nationalitäts-ID");
        }
        return null;
    }

    private void alleFahrerAnzeigen() {
        List<Fahrer> fahrer = FahrerService.alleFahrerAnzeigen();
        try {
            if (fahrer != null && !fahrer.isEmpty()) {
                for (Fahrer f : fahrer) {
                    System.out.println("Fahrer Nr: " + f.getFahrerId() + ", Vorname " + f.getVorname() + ", Nachname " + f.getNachname() + ", " + f.getNationalitaet() + ", " + f.getFahrzeug());
                }
            } else {
                System.err.println("Keine Fahrer gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Fahrer." + e.getMessage());
            e.printStackTrace();
        }
    }
}
