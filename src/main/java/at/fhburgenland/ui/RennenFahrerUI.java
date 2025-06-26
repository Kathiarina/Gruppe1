package at.fhburgenland.ui;

import at.fhburgenland.model.*;
import at.fhburgenland.service.FahrerService;
import at.fhburgenland.service.RennenFahrerService;
import at.fhburgenland.service.RennenService;
import at.fhburgenland.service.StatusService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RennenFahrerUI {
    private final Scanner scanner;
    private final Menu menu;

    public RennenFahrerUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void rennenFahrerMenu() {
        while (true) {
            menu.zeigeRennenFahrerMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleRennenFahrerAnzeigen();
                    break;
                case "2":
                    createRennenFahrer();
                    break;
                case "3":
                    alleRennenFahrerAnzeigen();
                    updateRennenFahrer();
                    break;
                case "4":
                    alleRennenFahrerAnzeigen();
                    deleteRennenFahrer();
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

    public void createRennenFahrer() {
        try {
            RennenFahrer rennenFahrer = new RennenFahrer();

            Fahrer fahrer = fahrerAuswaehlen();
            if (fahrer == null) {
                return;
            }
            rennenFahrer.setFahrer(fahrer);

            Rennen rennen = rennenAuswaehlen();
            if (rennen == null) {
                return;
            }
            rennenFahrer.setRennen(rennen);

            Status status = statusAuswaehlen();
            if (status == null) {
                return;
            }
            rennenFahrer.setStatus(status);

            RennenFahrerService.rennenFahrerHinzufuegen(rennenFahrer);
            System.out.println("Rennergebnis erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Rennergebnis konnte nicht angelegt werden." + e.getMessage());
        }

    }

    public void updateRennenFahrer() {
        try {
            int rennenId;
            int fahrerId;
            try {
                System.out.println("Bitte Rennen-ID eingeben:");
                rennenId = Integer.parseInt(scanner.nextLine());
                System.out.println("Bitte Fahrer-ID eingeben:");
                fahrerId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Rennergebnis-ID");
                return;
            }
            RennenFahrerId id = new RennenFahrerId(rennenId, fahrerId);
            RennenFahrer rennenFahrer = RennenFahrerService.rennenFahrerAnzeigenNachId(id);
            if (rennenFahrer == null) {
                System.out.println("Rennergebnis nicht gefunden.");
                return;
            }

            System.out.println("Möchten Sie den Fahrer ändern? (j/n):");
            String fahrerAendern = scanner.nextLine().toLowerCase();
            if (fahrerAendern.equals("j")) {
                Fahrer neuerFahrer = fahrerAuswaehlen();
                if (neuerFahrer != null) {
                    rennenFahrer.setFahrer(neuerFahrer);
                }
            }

            System.out.println("Möchten Sie das Rennen ändern? (j/n):");
            String rennenAendern = scanner.nextLine().toLowerCase();
            if (rennenAendern.equals("j")) {
                Rennen neuesRennen = rennenAuswaehlen();
                if (neuesRennen != null) {
                    rennenFahrer.setRennen(neuesRennen);
                }
            }

            System.out.println("Möchten Sie den Status ändern? (j/n):");
            String statusAendern = scanner.nextLine().toLowerCase();
            if (statusAendern.equals("j")) {
                Status neuerStatus = statusAuswaehlen();
                if (neuerStatus != null) {
                    rennenFahrer.setStatus(neuerStatus);
                }
            }

            RennenFahrerService.rennenFahrerUpdaten(rennenFahrer);
        } catch (Exception e) {
            System.err.println("Rennergebnis konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    public void deleteRennenFahrer() {
        try {
            int rennenId;
            int fahrerId;
            try {
                System.out.println("Bitte Rennen-ID eingeben:");
                rennenId = Integer.parseInt(scanner.nextLine());
                System.out.println("Bitte Fahrer-ID eingeben:");
                fahrerId = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.err.println("Ungültige Rennergebnis-ID");
                return;
            }
            RennenFahrerId id = new RennenFahrerId(rennenId, fahrerId);
            RennenFahrer rennenFahrer = RennenFahrerService.rennenFahrerAnzeigenNachId(id);
            if (rennenFahrer == null) {
                System.err.println("Rennergebnis kann nicht gelöscht werden, da es nicht existiert.");
                return;
            }

            if (rennenFahrer.getRennen() != null && rennenFahrer.getFahrer() != null) {
                System.err.println("Rennergebnis ist einem Rennen und einem Fahrer zugeordnet und kann daher nicht gelöscht werden.");
                return;
            }

            RennenFahrerService.rennenFahrerLoeschen(id);
            System.out.printf("Rennergebnis mit ID %s erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Rennergebnis konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    private Fahrer fahrerAuswaehlen() {
        List<Fahrer> fahrer = FahrerService.alleFahrerAnzeigen();
        if (fahrer.isEmpty()) {
            System.err.println("Es sind keine Fahrer vorhanden. Bitte zuerst einen Fahrer anlegen.");
            return null;
        }
        System.out.println("Verfügbare Fahrer:");
        for (Fahrer f : fahrer) {
            System.out.println("Fahrer Nr: " + f.getFahrerId() + ", Vorname " + f.getVorname() + ", Nachname " + f.getNachname() + ", " + f.getNationalitaet() + ", " + f.getFahrzeug());
        }
        System.out.println("Bitte Fahrer-ID auswählen:");
        int fahrerId;
        try {
            fahrerId = Integer.parseInt(scanner.nextLine());
            Fahrer ausgewaehlerFahrer = FahrerService.fahrerAnzeigenNachId(fahrerId);
            if (ausgewaehlerFahrer != null) {
                return ausgewaehlerFahrer;
            } else {
                System.err.println("Fahrer mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Fahrer-ID");
        }
        return null;
    }

    private Rennen rennenAuswaehlen() {
        List<Rennen> rennen = RennenService.alleRennenAnzeigen();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        if (rennen.isEmpty()) {
            System.err.println("Es sind keine Rennen vorhanden. Bitte zuerst ein Rennen anlegen.");
            return null;
        }
        System.out.println("Verfügbare Rennen:");
        for (Rennen r : rennen) {
            String datumUhrzeitFormatiert = r.getDatumUhrzeit().format(formatter);
            System.out.println("Rennen Nr: " + r.getRennenId() + ", Datum und Uhrzeit " + datumUhrzeitFormatiert + ", " + r.getRennstrecke());
        }
        System.out.println("Bitte Rennen-ID auswählen:");
        int rennenId;
        try {
            rennenId = Integer.parseInt(scanner.nextLine());
            Rennen ausgewaehltesRennen = RennenService.rennenAnzeigenNachId(rennenId);
            if (ausgewaehltesRennen != null) {
                return ausgewaehltesRennen;
            } else {
                System.err.println("Rennen mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Rennen-ID");
        }
        return null;
    }

    private Status statusAuswaehlen() {
        List<Status> status = StatusService.alleStatusAnzeigen();
        if (status.isEmpty()) {
            System.err.println("Es ist kein Status vorhanden.");
            return null;
        }
        System.out.println("Verfügbarer Status:");
        for (Status s : status) {
            System.out.println("Status Nr: " + s.getStatusId() + ", " + s.getStatusBeschreibung());
        }
        System.out.println("Bitte Status-ID auswählen:");
        int statusId;
        try {
            statusId = Integer.parseInt(scanner.nextLine());
            Status ausgewaehlterStatus = StatusService.statusAnzeigenNachId(statusId);
            if (ausgewaehlterStatus != null) {
                return ausgewaehlterStatus;
            } else {
                System.err.println("Status mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Status-ID");
        }
        return null;
    }

    private void alleRennenFahrerAnzeigen() {
        List<RennenFahrer> rennenFahrer = RennenFahrerService.alleRennenFahrerAnzeigen();
        try {
            if (rennenFahrer != null && !rennenFahrer.isEmpty()) {
                for (RennenFahrer rf : rennenFahrer) {
                    System.out.println("Rennergebnis Nr: " + rf.getRennenFahrerId() + ", " + rf.getFahrer() + ", " + rf.getRennen() + ", " + rf.getStatus() + ", Zeit " + rf.getZeit());
                }
            } else {
                System.err.println("Keine Rennergebnisse gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Rennergebnisse" + e.getMessage());
            e.printStackTrace();
        }
    }
}
