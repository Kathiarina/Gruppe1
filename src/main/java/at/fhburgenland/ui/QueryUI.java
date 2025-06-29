package at.fhburgenland.ui;

import at.fhburgenland.model.Rennstrecke;
import at.fhburgenland.service.QueryService;
import at.fhburgenland.service.RennstreckeService;

import java.util.List;
import java.util.Scanner;

/**
 * Die Benutzeroberfläche für die Abfragen wird in dieser Klasse dargestellt und die eingaben verarbeitet
 */
public class QueryUI {
    private final Scanner scanner;

    public QueryUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
    }

    /**
     * Startet das Abfragenmenü und listet Optionen für User
     */
    public void queriesMenu() {
        while (true) {
            Menu.zeigeQueriesMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    QueryService.teamPlatzierung();
                    break;
                case "2":
                    Rennstrecke ausgewaehlteRennstrecke = rennstreckeAuswaehlen();
                    if (ausgewaehlteRennstrecke != null) {
                        QueryService.beteiligteFahrzeugeAusgeben(ausgewaehlteRennstrecke.getRennstreckenId());
                    }
                    break;
                case "3":
                    System.out.println("Zurück zum Hauptmenü");
                    return;
                default:
                    System.err.println("Ungültige Eingabe.");
                    break;
            }
        }
    }

    /**
     * Listet alle verfügbaren Rennstrecken auf und lässt eine auswählen
     *
     * @return die ausgewählte Rennstrecke oder null bei einem Fehler
     */
    private Rennstrecke rennstreckeAuswaehlen() {
        List<Rennstrecke> rennstrecken = RennstreckeService.alleRennstreckenAnzeigen();
        if (rennstrecken.isEmpty()) {
            System.err.println("Es sind keine Rennstrecken vorhanden. Bitte zuerst eine Rennstrecke anlegen.");
            return null;
        }
        System.out.println("Verfügbare Rennstrecken:");
        for (Rennstrecke rennstrecke : rennstrecken) {
            System.out.println("Rennstrecke Nr: " + rennstrecke.getRennstreckenId() + ", Ort " + rennstrecke.getOrt() + ", Bundesland" + rennstrecke.getBundesland());
        }
        System.out.println("Bitte Rennstrecken-ID auswählen:");
        int rennsteckenId;
        try {
            rennsteckenId = Integer.parseInt(scanner.nextLine());
            Rennstrecke rennstrecke = RennstreckeService.rennstreckeAnzeigenNachId(rennsteckenId);
            if (rennstrecke != null) {
                return rennstrecke;
            } else {
                System.err.println("Rennstrecke mit dieser ID wurde nicht gefunden.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Rennstrecken-ID" + e.getMessage());
        }
        return null;
    }
}
