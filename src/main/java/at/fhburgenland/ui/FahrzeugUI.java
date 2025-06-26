package at.fhburgenland.ui;

import at.fhburgenland.model.Fahrzeug;
import at.fhburgenland.model.Fahrzeugtyp;
import at.fhburgenland.model.Team;
import at.fhburgenland.service.FahrzeugService;
import at.fhburgenland.service.FahrzeugtypService;
import at.fhburgenland.service.TeamService;

import java.util.List;
import java.util.Scanner;

public class FahrzeugUI {

    private final Scanner scanner;
    private final Menu menu;

    public FahrzeugUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void fahrzeugMenu() {
        while (true) {
            menu.zeigeFahrzeugMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleFahrzeugeAnzeigen();
                    break;
                case "2":
                    createFahrzeug();
                    break;
                case "3":
                    alleFahrzeugeAnzeigen();
                    updateFahrzeug();
                    break;
                case "4":
                    alleFahrzeugeAnzeigen();
                    deleteFahrzeug();
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

    public void createFahrzeug() {
        try {
            Fahrzeug fahrzeug = new Fahrzeug();

            Fahrzeugtyp fahrzeugtyp = fahrzeugtypAuswaehlen();
            if (fahrzeugtyp == null) {
                return;
            }
            fahrzeug.setFahrzeugtyp(fahrzeugtyp);

            Team team = teamAuswaehlen();
            if (team == null) {
                return;
            }
            fahrzeug.setTeam(team);

            FahrzeugService.fahrzeugHinzufuegen(fahrzeug);
            System.out.println("Fahrzeug erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Fahrzeug konnte nicht angelegt werden." + e.getMessage());
        }
    }

    public void updateFahrzeug() {
        try {
            System.out.println("Bitte die ID des zu bearbeitenden Fahrzeugs eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Fahrzeug-ID");
                return;
            }

            Fahrzeug fahrzeug = FahrzeugService.fahrzeugAnzeigenNachId(id);
            if (fahrzeug == null) {
                System.out.println("Fahrzeug nicht gefunden.");
                return;
            }

            System.out.println("Möchten Sie den Fahrzeugtyp ändern? (j/n):");
            String fahrzeugtypAendern = scanner.nextLine().toLowerCase();
            if (fahrzeugtypAendern.equals("j")) {
                Fahrzeugtyp neuerFahrzeugtyp = fahrzeugtypAuswaehlen();
                if (neuerFahrzeugtyp != null) {
                    fahrzeug.setFahrzeugtyp(neuerFahrzeugtyp);
                }
            }

            System.out.println("Möchten Sie das Team ändern? (j/n):");
            String teamAendern = scanner.nextLine().toLowerCase();
            if (teamAendern.equals("j")) {
                Team neuesTeam = teamAuswaehlen();
                if (neuesTeam != null) {
                    fahrzeug.setTeam(neuesTeam);
                }
            }
            FahrzeugService.fahrzeugUpdaten(fahrzeug);
        } catch (
                Exception e) {
            System.err.println("Fahrzeug konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    public void deleteFahrzeug() {
        try {
            System.out.println("Bitte die ID des zu löschenden Fahrzeugs eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Fahrzeug-ID");
                return;
            }

            Fahrzeug fahrzeug = FahrzeugService.fahrzeugAnzeigenNachId(id);
            if (fahrzeug == null) {
                System.err.println("Fahrzeug kann nicht gelöscht werden, da es nicht existiert.");
                return;
            }

            String begruendung = FahrzeugService.pruefeVerknuepfungMitFahrzeug(fahrzeug);
            if (begruendung != null) {
                System.err.println(begruendung);
                return;
            }

            FahrzeugService.fahrzeugLoeschen(id);
            System.out.printf("Fahrzeug mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Fahrzeug konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    private Fahrzeugtyp fahrzeugtypAuswaehlen() {
        List<Fahrzeugtyp> fahrzeugtypen = FahrzeugtypService.alleFahrzeugtypenAnzeigen();
        if (fahrzeugtypen == null || fahrzeugtypen.isEmpty()) {
            System.err.println("Es sind keine Fahrzeugtypen vorhanden. Bitte zuerst einen Fahrzeugtyp anlegen.");
            return null;
        }
        System.out.println("Verfügbare Fahrzeugtypen:");
        for (Fahrzeugtyp fahrzeugtyp : fahrzeugtypen) {
            System.out.println("Fahrzeugtyp Nr: " + fahrzeugtyp.getFahrzeugtypId() + ", Modell " + fahrzeugtyp.getModell() + ", Motor " + fahrzeugtyp.getMotor() + ", Gewicht " + fahrzeugtyp.getGewichtKg() + "kg");
        }

        System.out.println("Bitte Fahrzeugtyp-ID auswählen:");
        int fahrzeugtypId;
        try {
            fahrzeugtypId = Integer.parseInt(scanner.nextLine());
            Fahrzeugtyp fahrzeugtyp = FahrzeugtypService.fahrzeugtypAnzeigenNachId(fahrzeugtypId);
            if (fahrzeugtyp != null) {
                return fahrzeugtyp;
            } else {
                System.err.println("Fahrzeugtyp mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Fahrzeugtyp-ID");
        }
        return null;
    }

    private Team teamAuswaehlen() {
        List<Team> teams = TeamService.alleTeamsAnzeigen();
        if (teams.isEmpty()) {
            System.err.println("Es sind keine Teams vorhanden. Bitte zuerst ein Team anlegen.");
            return null;
        }
        System.out.println("Verfügbare Teams:");
        for (Team team : teams) {
            System.out.println("Team Nr: " + team.getTeamId() + ", Teamname " + team.getTeamName() + ", Gründungsjahr " + team.getGruendungsjahr() + ", " + team.getNationalitaet() + ", " + team.getHauptsponsor());
        }
        System.out.println("Bitte Team-ID auswählen:");
        int teamId;
        try {
            teamId = Integer.parseInt(scanner.nextLine());
            Team team = TeamService.teamAnzeigenNachId(teamId);
            if (team != null) {
                return team;
            } else {
                System.err.println("Team mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Team-ID");
        }
        return null;
    }

    private void alleFahrzeugeAnzeigen() {
        List<Fahrzeug> fahrzeuge = FahrzeugService.alleFahrzeugeAnzeigen();
        try {
            if (fahrzeuge != null && !fahrzeuge.isEmpty()) {
                for (Fahrzeug fahrzeug : fahrzeuge) {
                    System.out.println("Fahrzeug Nr: " + fahrzeug.getFahrzeugId() + ", " + fahrzeug.getFahrzeugtyp() + ", " + fahrzeug.getTeam());
                }
            } else {
                System.err.println("Keine Fahrzeuge gefunden.");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Fahrzeuge." + e.getMessage());
            e.printStackTrace();
        }
    }
}
