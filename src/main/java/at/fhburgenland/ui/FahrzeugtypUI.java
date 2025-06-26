package at.fhburgenland.ui;

import at.fhburgenland.model.Fahrzeugtyp;
import at.fhburgenland.service.FahrzeugtypService;

import java.util.List;
import java.util.Scanner;

public class FahrzeugtypUI {
    private final Scanner scanner;
    private final Menu menu;

    public FahrzeugtypUI(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void fahrzeugtypMenu() {
        while (true) {
            menu.zeigeFahrzeugtypMenu();
            String userEingabe = scanner.nextLine();

            switch (userEingabe) {
                case "1":
                    alleFahrzeugtypenAnzeigen();
                    break;
                case "2":
                    createFahrzeugtyp();
                    break;
                case "3":
                    alleFahrzeugtypenAnzeigen();
                    updateFahrzeugtyp();
                    break;
                case "4":
                    alleFahrzeugtypenAnzeigen();
                    deleteFahrzeugtyp();
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

    public void createFahrzeugtyp() {
        try {
            Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
            System.out.println("Bitte das Modell des Fahrzeugtyps eingeben:");
            String modell = scanner.nextLine();
            if (modell.isEmpty()) {
                System.err.println("Modell darf nicht leer sein.");
                return;
            }
            fahrzeugtyp.setModell(modell);
            System.out.println("Bitte den Motor des Fahrzeugtyps eingeben:");
            String motor = scanner.nextLine();
            if (motor.isEmpty()) {
                System.err.println("Motor darf nicht leer sein.");
                return;
            }
            fahrzeugtyp.setMotor(motor);

            System.out.println("Bitte das Gewicht des Fahrzeugtyps eingeben (Ganze Zahl):");
            String gewichtEingabe = scanner.nextLine();
            if (!gewichtEingabe.isBlank()) {
                try {
                    int neuesGewicht = Integer.parseInt(gewichtEingabe);
                    fahrzeugtyp.setGewichtKg(neuesGewicht);
                } catch (NumberFormatException e) {
                    System.out.println("Ungültiges Gewicht. Bitte geben Sie eine Ganzzahl ein.");
                    return;
                }
            }
            FahrzeugtypService.fahrzeugtypHinzufuegen(fahrzeugtyp);
            System.out.println("Fahrzeugtyp erfolgreich gespeichert.");
        } catch (Exception e) {
            System.err.println("Fahrzeugtyp konnte nicht angelegt werden." + e.getMessage());
        }

    }

    public void updateFahrzeugtyp() {
        try {
            System.out.println("Bitte die ID des zu bearbeitenden Fahrzeugtyps eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Fahrzeugtyp-ID");
                return;
            }

            Fahrzeugtyp fahrzeugtyp = FahrzeugtypService.fahrzeugtypAnzeigenNachId(id);
            if (fahrzeugtyp == null) {
                System.out.println("Fahrzeugtyp nicht gefunden.");
                return;
            }

            System.out.println("Neues Modell eingeben (Enter zum überspringen):");
            String neuesModell = scanner.nextLine();
            if (!neuesModell.isBlank()) {
                fahrzeugtyp.setModell(neuesModell);
            }
            System.out.println("Neuen Motor eingeben (Enter zum überspringen):");
            String neuerMotor = scanner.nextLine();
            if (!neuerMotor.isBlank()) {
                fahrzeugtyp.setMotor(neuerMotor);
            }

            System.out.println("Neues Gewicht eingeben (Enter zum überspringen):");
            String gewichtEingabe = scanner.nextLine();
            if (!gewichtEingabe.isBlank()) {
                try {
                    int neuesGewicht = Integer.parseInt(gewichtEingabe);
                    if (neuesGewicht <= 0) {
                        System.out.println("Gewicht muss positiv sein.");
                        return;
                    }
                    fahrzeugtyp.setGewichtKg(neuesGewicht);
                } catch (NumberFormatException e) {
                    System.out.println("Ungültiges Gewicht. Bitte geben Sie eine Ganzzahl ein.");
                    return;
                }
            }
            FahrzeugtypService.fahrzeugtypUpdaten(fahrzeugtyp);
        } catch (Exception e) {
            System.err.println("Fahrzeugtyp konnte nicht bearbeitet werden." + e.getMessage());
        }
    }

    public void deleteFahrzeugtyp() {
        try {
            System.out.println("Bitte die ID des zu löschenden Fahrzeugtyps eingeben:");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Fahrzeugtyp-ID");
                return;
            }

            Fahrzeugtyp fahrzeugtyp = FahrzeugtypService.fahrzeugtypAnzeigenNachId(id);
            if (fahrzeugtyp == null) {
                System.err.println("Fahrzeugtyp kann nicht gelöscht werden, da er nicht existiert.");
                return;
            }

            if(!fahrzeugtyp.getFahrzeug().isEmpty()) {
                System.err.println("Fahrzeugtyp ist einem Fahrzeug zugeordnet und kann daher nicht gelöscht werden.");
                return;
            }

            FahrzeugtypService.fahrzeugtypLoeschen(id);
            System.out.printf("Fahrzeugtyp mit ID %d erfolgreich gelöscht.%n", id);
        } catch (Exception e) {
            System.err.println("Fahrzeugtyp konnte nicht gelöscht werden." + e.getMessage());
        }
    }

    private void alleFahrzeugtypenAnzeigen() {
        List<Fahrzeugtyp> fahrzeugtypen = FahrzeugtypService.alleFahrzeugtypenAnzeigen();
        try {
            if (fahrzeugtypen != null && !fahrzeugtypen.isEmpty()) {
                for (Fahrzeugtyp fahrzeugtyp : fahrzeugtypen) {
                    System.out.println("Fahrzeugtyp Nr: " + fahrzeugtyp.getFahrzeugtypId() + ", Modell " + fahrzeugtyp.getModell() + ", Motor " + fahrzeugtyp.getMotor() + ", Gewicht " + fahrzeugtyp.getGewichtKg() + "kg");
                }
            } else {
                System.err.println("Keine Fahrzeugtypen gefunden.");
            }
        }catch (Exception e) {
            System.err.println("Fehler beim Anzeigen der Fahrzeugtypen" + e.getMessage());
            e.printStackTrace();
        }

    }
}
