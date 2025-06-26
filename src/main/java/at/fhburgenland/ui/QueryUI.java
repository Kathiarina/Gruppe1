package at.fhburgenland.ui;

import at.fhburgenland.service.QueryService;

import java.util.Scanner;

public class QueryUI {
    private final Scanner scanner;
    private final Menu menu;
    QueryService queryService = new QueryService();

    public QueryUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void queriesMenu() {
        while (true) {
            menu.zeigeQueriesMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":

                    queryService.teamPlatzierung();
                    break;
                case "2":
                    queryService.beteiligteFahrzeugeAusgeben(2);
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
}
