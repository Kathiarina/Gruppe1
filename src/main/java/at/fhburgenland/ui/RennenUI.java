package at.fhburgenland.ui;

import at.fhburgenland.model.Rennen;
import at.fhburgenland.model.Rennstrecke;
import at.fhburgenland.service.RennenService;
import at.fhburgenland.service.RennstreckeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class RennenUI {
    private final Scanner scanner;

    public RennenUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
    }

    public void rennenMenu() {
        while (true) {
            Menu.zeigeRennenMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleRennenAnzeigen();
                    break;
                case "2":
                    createRennen();
                    break;
                case "3":
                    alleRennenAnzeigen();
                    updateRennen();
                    break;
                case "4":
                    alleRennenAnzeigen();
                    deleteRennen();
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

    public void createRennen() {
        try {
            Rennen rennen = new Rennen();
            System.out.println("Bitte das Datum und die Uhrzeit des Rennens eingeben (Format: DD.MM.YYYY HH:MM):");
            String datumUhrzeitEingabe = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime datumUhrzeit;

            try {
                datumUhrzeit = LocalDateTime.parse(datumUhrzeitEingabe, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Ungültiges Format. Bitte im Format DD.MM.YYYY HH:MM eingeben.");
                return;
            }

            rennen.setDatumUhrzeit(datumUhrzeit);

            Rennstrecke rennstrecke = rennstreckeAuswaehlen();
            if (rennstrecke == null) {
                return;
            }
            rennen.setRennstrecke(rennstrecke);

            RennenService.rennenHinzufuegen(rennen);
            System.out.println("Rennen erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Rennen konnte nicht angelegt werden." + e.getMessage());
        }

    }

    public void updateRennen() {
        try {
            System.out.println("Bitte die ID des zu bearbeitenden Rennens eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Rennen-ID");
                return;
            }

            Rennen rennen = RennenService.rennenAnzeigenNachId(id);
            if (rennen == null) {
                System.out.println("Rennen nicht gefunden.");
                return;
            }

            System.out.println("Neues Datum und Uhrzeit eingeben (Format: DD.MM.YYYY HH:MM):");
            String neuesDatumUhrzeitEingabe = scanner.nextLine();
            if (!neuesDatumUhrzeitEingabe.isBlank()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    LocalDateTime neuesDatumUhrzeit = LocalDateTime.parse(neuesDatumUhrzeitEingabe, formatter);
                    rennen.setDatumUhrzeit(neuesDatumUhrzeit);
                } catch (DateTimeParseException e) {
                    System.err.println("Ungültiges Format. Bitte im Format DD.MM.YYYY HH:MM eingeben.");
                    return;
                }
            }

            System.out.println("Möchten Sie die Rennstrecke ändern? (j/n):");
            String rennstreckeAendern = scanner.nextLine().toLowerCase();
            if (rennstreckeAendern.equals("j")) {
                Rennstrecke neueRennstrecke = rennstreckeAuswaehlen();
                if (neueRennstrecke != null) {
                    rennen.setRennstrecke(neueRennstrecke);
                }
            }
            RennenService.rennenUpdaten(rennen);
        } catch (Exception e) {
            System.err.println("Rennen konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    public void deleteRennen() {
        try {
            System.out.println("Bitte die ID des zu löschenden Rennens eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Rennen-ID");
                return;
            }

            Rennen rennen = RennenService.rennenAnzeigenNachId(id);
            if (rennen == null) {
                System.err.println("Rennen kann nicht gelöscht werden, da er nicht existiert.");
                return;
            }

            if (rennen.getRennstrecke() != null) {
                System.err.println("Rennen ist einer Rennstrecke zugeordnet und kann daher nicht gelöscht werden.");
                return;
            }

            RennenService.rennenLoeschen(id);
            System.out.printf("Rennen mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Rennen konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    private Rennstrecke rennstreckeAuswaehlen() {
        List<Rennstrecke> rennstrecken = RennstreckeService.alleRennstreckenAnzeigen();
        if (rennstrecken.isEmpty()) {
            System.err.println("Es sind keine Rennstrecken vorhanden. Bitte zuerst eine Rennstrecke anlegen.");
            return null;
        }
        System.out.println("Verfügbare Rennstrecken:");
        for (Rennstrecke rennstrecke : rennstrecken) {
            System.out.println("Rennstrecke Nr: " + rennstrecke.getRennstreckenId() + ", Ort " + rennstrecke.getOrt() + ", Bundesland " + rennstrecke.getBundesland());
        }
        System.out.println("Bitte Rennstrecken-ID auswählen:");
        int rennstreckenId;
        try {
            rennstreckenId = Integer.parseInt(scanner.nextLine());
            Rennstrecke rennstrecke = RennstreckeService.rennstreckeAnzeigenNachId(rennstreckenId);
            if (rennstrecke != null) {
                return rennstrecke;
            } else {
                System.err.println("Rennstrecke mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Rennstrecken-ID");
        }
        return null;
    }

    private void alleRennenAnzeigen() {
        List<Rennen> rennen = RennenService.alleRennenAnzeigen();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        try {
            if (rennen != null && !rennen.isEmpty()) {
                for (Rennen r : rennen) {
                    String datumUhrzeitFormatiert = r.getDatumUhrzeit().format(formatter);
                    System.out.println("Rennen Nr: " + r.getRennenId() + ", Datum und Uhrzeit " + datumUhrzeitFormatiert + ", " + r.getRennstrecke());
                }
            } else {
                System.err.println("Keine Rennen gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Rennen" + e.getMessage());
            e.printStackTrace();
        }
    }
}
