package at.fhburgenland.ui;

import at.fhburgenland.model.Hauptsponsor;
import at.fhburgenland.model.Nationalitaet;
import at.fhburgenland.model.Team;
import at.fhburgenland.service.HauptsponsorService;
import at.fhburgenland.service.NationalitaetService;
import at.fhburgenland.service.TeamService;

import java.util.List;
import java.util.Scanner;

/**
 * Benutzeroberfläche zur Verwaltung von Teams
 * Bietet Funktionen zur Anzeige, Erstellung, Bearbeitung und Löschung von Teams
 */
public class TeamUI {
    private final Scanner scanner;

    public TeamUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
    }

    /**
     * Startet das Teammenü und listet Optionen für User
     */
    public void teamMenu() {
        while (true) {
            Menu.zeigeTeamMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleTeamsAnzeigen();
                    break;
                case "2":
                    createTeam();
                    break;
                case "3":
                    alleTeamsAnzeigen();
                    updateTeam();
                    break;
                case "4":
                    alleTeamsAnzeigen();
                    deleteTeam();
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
     * Erstellt ein neues Team mit zugewiesenem Hauptsponsor
     */
    public void createTeam() {
        try {
            Team team = new Team();
            System.out.println("Bitte den Teamnamen eingeben:");
            String teamName = scanner.nextLine();
            if (teamName.isEmpty()) {
                System.err.println("Teamname darf nicht leer sein.");
                return;
            }
            team.setTeamName(teamName);

            System.out.println("Bitte das Gründungsjahr des Teams eingeben (Ganze Zahl):");
            String gruendungsjahrEingabe = scanner.nextLine();
            if (!gruendungsjahrEingabe.isBlank()) {
                try {
                    int neuesGruendungsjahr = Integer.parseInt(gruendungsjahrEingabe);
                    team.setGruendungsjahr(neuesGruendungsjahr);
                } catch (NumberFormatException e) {
                    System.out.println("Ungültiges Gründungsjahr. Bitte geben Sie eine Ganzzahl ein.");
                    return;
                }
            }
            Nationalitaet nationalitaet = nationalitaetAuswaehlen();
            if (nationalitaet == null) {
                return;
            }
            team.setNationalitaet(nationalitaet);

            Hauptsponsor hauptsponsor = hauptsponsorAuswaehlen();
            if (hauptsponsor == null) {
                return;
            }
            if (TeamService.hauptsponsorBereitsVergeben(hauptsponsor, -1)) {
                System.err.println("Dieser Hauptsponsor ist bereits einem anderen Team zugeordnet.");
                return;
            }
            team.setHauptsponsor(hauptsponsor);

            TeamService.teamHinzufuegen(team);
            System.out.println("Team erfolgreich gespeichert.");

        } catch (Exception e) {
            System.err.println("Team konnte nicht angelegt werden." + e.getMessage());
        }
    }

    /**
     * Aktualisiert die Daten eines bereits existierenden Teams
     */
    public void updateTeam() {
        try {
            System.out.println("Bitte die ID des zu bearbeitenden Teams eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Team-ID");
                return;
            }

            Team team = TeamService.teamAnzeigenNachId(id);
            if (team == null) {
                System.out.println("Team nicht gefunden.");
                return;
            }

            System.out.println("Neuen Teamnamen eingeben (Enter zum überspringen):");
            String neuerTeamname = scanner.nextLine();
            if (!neuerTeamname.isBlank()) {
                team.setTeamName(neuerTeamname);
            }
            System.out.println("Neues Gründungsjahr eingeben (Enter zum überspringen):");
            String gruendungsjahrEingabe = scanner.nextLine();
            if (!gruendungsjahrEingabe.isBlank()) {
                try {
                    int neuesGruendungsjahr = Integer.parseInt(gruendungsjahrEingabe);
                    if (neuesGruendungsjahr <= 0) {
                        System.out.println("Gründungsjahr muss positiv sein.");
                        return;
                    }
                    team.setGruendungsjahr(neuesGruendungsjahr);
                } catch (NumberFormatException e) {
                    System.out.println("Ungültiges Gründungsjahr. Bitte geben Sie eine Ganzzahl ein.");
                    return;
                }
            }

            System.out.println("Möchten Sie die Nationalität ändern? (j/n):");
            String nationalitaetAendern = scanner.nextLine().toLowerCase();
            if (nationalitaetAendern.equals("j")) {
                Nationalitaet neueNationalitaet = nationalitaetAuswaehlen();
                if (neueNationalitaet != null) {
                    team.setNationalitaet(neueNationalitaet);
                }
            }

            System.out.println("Möchten Sie den Hauptsponsor ändern? (j/n):");
            String hauptsponsorAendern = scanner.nextLine().toLowerCase();
            if (hauptsponsorAendern.equals("j")) {
                Hauptsponsor neuerHauptsponsor = hauptsponsorAuswaehlen();
                if (neuerHauptsponsor != null) {
                    if (TeamService.hauptsponsorBereitsVergeben(neuerHauptsponsor, team.getTeamId())) {
                        System.err.println("Dieser Hauptsponsor ist bereits vergeben.");
                        return;
                    }
                    team.setHauptsponsor(neuerHauptsponsor);
                }
            }
            TeamService.teamUpdaten(team);
        } catch (
                Exception e) {
            System.err.println("Team konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    /**
     * Löscht ein Team, wenn keine Verknüpfungen bestehen
     */
    public void deleteTeam() {
        try {
            System.out.println("Bitte die ID des zu löschenden Teams eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Team-ID");
                return;
            }

            Team team = TeamService.teamAnzeigenNachId(id);
            if (team == null) {
                System.err.println("Team kann nicht gelöscht werden, da es nicht existiert.");
                return;
            }

            String begruendung = TeamService.pruefeVerknuepfungMitTeam(team);
            if (begruendung != null) {
                System.err.println(begruendung);
                return;
            }

            TeamService.teamLoeschen(id);
            System.out.printf("Team mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Team konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    /**
     * Zeigt eine Liste der verfügbaren Hauptsponsoren zur Auswahl an
     *
     * @return den ausgewählten Hauptsponsor oder null bei einem Fehler
     */
    private Hauptsponsor hauptsponsorAuswaehlen() {
        List<Hauptsponsor> hauptsponsoren = HauptsponsorService.alleHauptsponsorenAnzeigen();
        if (hauptsponsoren == null || hauptsponsoren.isEmpty()) {
            System.err.println("Es sind keine Hauptsponsoren vorhanden. Bitte zuerst einen Hauptsponsor anlegen.");
            return null;
        }
        System.out.println("Verfügbare Hauptsponsoren:");
        for (Hauptsponsor hauptsponsor : hauptsponsoren) {
            System.out.println("Hauptsponsor Nr: " + hauptsponsor.getHauptsponsorId() + ", Hauptsponsorname " + hauptsponsor.getHauptsponsorName() + ", Jährliche Sponsorsumme " + hauptsponsor.getJaehrlicheSponsorsumme() + "€");
        }
        System.out.println("Bitte Hauptsponsor-ID auswählen:");
        int hauptsponsorId;
        try {
            hauptsponsorId = Integer.parseInt(scanner.nextLine());
            Hauptsponsor hauptsponsor = HauptsponsorService.hauptsponsorAnzeigenNachId(hauptsponsorId);
            if (hauptsponsor != null) {
                return hauptsponsor;
            } else {
                System.err.println("Hauptsponsor mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Hauptsponsor-ID");
        }
        return null;
    }

    /**
     * Zeigt eine Liste der verfügbaren Nationalitäten zur Auswahl an
     *
     * @return die ausgewählte Nationalität oder null bei einem Fehler
     */
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

    /**
     * Zeigt alle vorhandenen Teams an
     */
    private void alleTeamsAnzeigen() {
        List<Team> team = TeamService.alleTeamsAnzeigen();
        try {
            if (team != null && !team.isEmpty()) {
                for (Team t : team) {
                    System.out.println("Team Nr: " + t.getTeamId() + ", Teamname " + t.getTeamName() + ", Gründungsjahr " + t.getGruendungsjahr() + ", " + t.getNationalitaet() + ", " + t.getHauptsponsor());
                }
            } else {
                System.err.println("Keine Teams gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Teams." + e.getMessage());
        }
    }
}
