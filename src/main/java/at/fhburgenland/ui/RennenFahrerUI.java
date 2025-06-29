package at.fhburgenland.ui;

import at.fhburgenland.model.*;
import at.fhburgenland.service.FahrerService;
import at.fhburgenland.service.RennenFahrerService;
import at.fhburgenland.service.RennenService;
import at.fhburgenland.service.StatusService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Benutzeroberfläche zur Verwaltung von Rennergebnissen
 * Bietet Funktionen zur Anzeige, Erstellung, Bearbeitung und Löschung von Rennergebnissen
 */
public class RennenFahrerUI {
    private final Scanner scanner;

    public RennenFahrerUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
    }

    /**
     * Startet das Rennergebnismenü und listet Optionen für User
     */
    public void rennenFahrerMenu() {
        while (true) {
            Menu.zeigeRennenFahrerMenu();
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

    /**
     * Erstellt ein neues Rennergebnis
     */
    public void createRennenFahrer() {
        try {
            Fahrer fahrer = fahrerAuswaehlen();
            if (fahrer == null) {
                return;
            }

            Rennen rennen = rennenAuswaehlen();
            if (rennen == null) {
                return;
            }

            RennenFahrerId id = new RennenFahrerId(rennen.getRennenId(), fahrer.getFahrerId());
            if (RennenFahrerService.rennenFahrerAnzeigenNachId(id) != null) {
                System.err.println("Dieser Fahrer hat für dieses Rennen bereits ein Ergebnis eingetragen.");
                System.err.println("Zum Ändern bitte Menüpunkt 3 auswählen.");
                return;
            }

            Status status = statusAuswaehlen();
            if (status == null) {
                return;
            }

            String platzierung = status.getStatusBeschreibung();

            String zeit = zeitEingebenWennErforderlich(platzierung);
            if (zeiteingabeErforderlichFuerStatus(platzierung) && zeit == null) {
                return;
            }

            RennenFahrer rennenFahrer = new RennenFahrer(fahrer, rennen, status, zeit);
            RennenFahrerService.rennenFahrerHinzufuegen(rennenFahrer);
            System.out.println("Rennergebnis erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Rennergebnis konnte nicht angelegt werden." + e.getMessage());
        }
    }

    /**
     * Aktualisiert die Daten eines bereits existierenden Rennergebnisses
     */
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
            RennenFahrer rennenFahrerAlt = RennenFahrerService.rennenFahrerAnzeigenNachId(id);
            if (rennenFahrerAlt == null) {
                System.out.println("Rennergebnis nicht gefunden.");
                return;
            }

            Fahrer aktuellerFahrer = rennenFahrerAlt.getFahrer();
            Rennen aktuellesRennen = rennenFahrerAlt.getRennen();
            Status aktuellerStatus = rennenFahrerAlt.getStatus();
            String aktuelleZeit = rennenFahrerAlt.getZeit();

            System.out.println("Möchten Sie den Fahrer ändern? (j/n):");
            String fahrerAendern = scanner.nextLine().toLowerCase();
            Fahrer neuerFahrer;
            if (fahrerAendern.equals("j")) {
                neuerFahrer = fahrerAuswaehlen();
                if (neuerFahrer == null) {
                    System.err.println("Kein gültiger Fahrer ausgewählt.");
                    return;
                }
            } else {
                neuerFahrer = aktuellerFahrer;
            }

            System.out.println("Möchten Sie das Rennen ändern? (j/n):");
            String rennenAendern = scanner.nextLine().toLowerCase();
            Rennen neuesRennen;
            if (rennenAendern.equals("j")) {
                neuesRennen = rennenAuswaehlen();
                if (neuesRennen == null) {
                    System.err.println("Kein gültiges Rennen ausgewählt.");
                    return;
                }
            } else {
                neuesRennen = aktuellesRennen;
            }

            System.out.println("Möchten Sie den Status ändern? (j/n):");
            String statusAendern = scanner.nextLine().toLowerCase();
            Status neuerStatus;
            if (statusAendern.equals("j")) {
                neuerStatus = statusAuswaehlen();
                if (neuerStatus == null) {
                    System.err.println("Kein gültiger Status ausgewählt.");
                    return;
                }
            } else {
                neuerStatus = aktuellerStatus;
            }

            System.out.println("Möchten Sie die Zeit ändern? (j/n):");
            String zeitAendern = scanner.nextLine().toLowerCase();
            String neueZeit = aktuelleZeit;
            if (zeitAendern.equals("j")) {
                String platzierung = neuerStatus.getStatusBeschreibung();
                neueZeit = zeitEingebenWennErforderlich(platzierung);
                if (zeiteingabeErforderlichFuerStatus(platzierung) && neueZeit == null) {
                    return;
                }
            }

            if (neuerFahrer.getFahrerId() != aktuellerFahrer.getFahrerId()
                    || neuesRennen.getRennenId() != aktuellesRennen.getRennenId()) {
                RennenFahrerId neueId = new RennenFahrerId(neuesRennen.getRennenId(), neuerFahrer.getFahrerId());
                if (RennenFahrerService.rennenFahrerAnzeigenNachId(neueId) != null) {
                    System.err.println("Ein Rennergebnis mit dieser Kombination aus Fahrer und Rennen existiert bereits. Änderung ist nicht möglich.");
                    return;
                }
            }
            RennenFahrerService.rennenFahrerLoeschen(rennenFahrerAlt.getRennenFahrerId());

            RennenFahrer neuerEintrag = new RennenFahrer(neuerFahrer, neuesRennen, neuerStatus, neueZeit);

            RennenFahrerService.rennenFahrerHinzufuegen(neuerEintrag);
            System.out.println("Rennergebnis erfolgreich aktualisiert.");
        } catch (Exception e) {
            System.err.println("Rennergebnis konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    /**
     * Löscht ein Rennergebnis
     */
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

            RennenFahrerService.rennenFahrerLoeschen(id);
            System.out.printf("Rennergebnis mit ID %s erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Rennergebnis konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    /**
     * Zeigt eine Liste der verfügbaren Fahrer zur Auswahl an
     *
     * @return der ausgewählte Fahrer oder null bei einem Fehler
     */
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

    /**
     * Zeigt eine Liste der verfügbaren Rennen zur Auswahl an
     *
     * @return das ausgewählte Rennen oder null bei einem Fehler
     */
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

    /**
     * Zeigt eine Liste des verfügbaren Status zur Auswahl an
     *
     * @return der ausgewählte Status oder null bei einem Fehler
     */
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

    /**
     * Fragt eine präzise Zeitangabe vom Benutzer ab
     * Prüft auf gültige numerische Eingaben
     * Gibt die Zeit im Format HH:MM:SS.mmm zurück
     */
    private String eingabeZeit() {
        try {

            System.out.println("Bitte die Zeit für das gefahrene Rennen eingeben:");
            System.out.println("Stunden eingeben (z.B. 1): ");
            String stundenEingabe = scanner.nextLine();
            if (stundenEingabe.isBlank()) {
                return null;
            }
            int stunden = Integer.parseInt(stundenEingabe);

            System.out.println("Minuten eingeben (z.B. 22): ");
            String minutenEingabe = scanner.nextLine();
            if (minutenEingabe.isBlank()) {
                return null;
            }
            int minuten = Integer.parseInt(minutenEingabe);

            System.out.println("Sekunden mit Millisekunden eingeben (z.B. 22.798): ");
            String sekundenEingabe = scanner.nextLine();
            if (sekundenEingabe.isBlank()) {
                return null;
            }
            double sekundenKomplett = Double.parseDouble(sekundenEingabe);

            int sekunden = (int) sekundenKomplett;
            int millisekunden = (int) Math.round((sekundenKomplett - sekunden) * 1000);

            if (stunden < 0 || minuten < 0 || minuten >= 60 ||
                    sekunden < 0 || sekunden >= 60 ||
                    millisekunden < 0 || millisekunden >= 1000) {
                System.err.println("Ungültige Zeitangabe. Bitte gültige Werte eingeben.");
                return null;
            }

            return String.format("%02d:%02d:%02d.%03d", stunden, minuten, sekunden, millisekunden);
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Eingabe. Bitte Zahlen eingeben.");
            return null;
        }
    }

    /**
     * Prüft, ob man für den Status eine Zeit eingeben muss
     *
     * @param statusBeschreibung Beschreibung des Status
     * @return true, wenn eine Zeitangabe notwendig ist, sonst false
     */
    private boolean zeiteingabeErforderlichFuerStatus(String statusBeschreibung) {
        return statusBeschreibung != null &&
                (statusBeschreibung.equalsIgnoreCase("Erster Platz") ||
                        statusBeschreibung.equalsIgnoreCase("Zweiter Platz") ||
                        statusBeschreibung.equalsIgnoreCase("Dritter Platz") ||
                        statusBeschreibung.equalsIgnoreCase("Teilgenommen"));
    }

    /**
     * Lässt den Benutzer Zeit eingeben, wenn sie für den gewählten Status erforderlich ist
     *
     * @param statusBeschreibung der gewählte Status
     * @return Die eingegebene Zeit oder null bei ungültigen Werten
     */

    private String zeitEingebenWennErforderlich(String statusBeschreibung) {
        if ("Ausgeschieden".equalsIgnoreCase(statusBeschreibung)) {
            System.out.println("Fahrer ist ausgeschieden, keine Zeiteingabe möglich.");
            return null;
        }

        if (zeiteingabeErforderlichFuerStatus(statusBeschreibung)) {
            String zeit = eingabeZeit();
            if (zeit == null) {
                System.err.println("Bei diesem Status muss eine gültige Zeit eingegeben werden.");
                return null;
            }
            return zeit;
        }
        return null;
    }

    /**
     * Zeigt alle vorhandenen Rennergebnisse an
     */
    private void alleRennenFahrerAnzeigen() {
        List<RennenFahrer> rennenFahrer = RennenFahrerService.alleRennenFahrerAnzeigen();
        try {
            if (rennenFahrer != null && !rennenFahrer.isEmpty()) {
                for (RennenFahrer rf : rennenFahrer) {
                    System.out.printf("RennenID: %2d | FahrerID: %2d | Status: %-13s | Zeit: %s%n",
                            rf.getRennenFahrerId().getRennenId(),
                            rf.getRennenFahrerId().getFahrerId(),
                            rf.getStatus().getStatusBeschreibung(),
                            rf.getZeit());
                }
            } else {
                System.err.println("Keine Rennergebnisse gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Rennergebnisse" + e.getMessage());
        }
    }
}
