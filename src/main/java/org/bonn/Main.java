package org.bonn;

import org.bonn.lzu.Classes.ComponentAssembler;
import org.bonn.lzu.Command.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        // Beispiel Pfad:
        // src/test/resources/classLoader/jarDir/codesOOKA-1.0-SNAPSHOT.jar

        ComponentOperationExecutor componentOperationExecutor = new ComponentOperationExecutor();
        ComponentAssembler componentAssembler = new ComponentAssembler();
        String componentID = null;
        Scanner scanner = new Scanner(System.in);

        printMenuOptions();
        while (true) {
            Thread.sleep(1000);

            if (componentID != null) {
                System.out.println("==========================================");
            }
            System.out.println("\nIhre Eingabe:");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Bitte geben Sie den Namen der Komponente ein:");
                    String componentName = scanner.nextLine();
                    System.out.println("Bitte geben Sie den Pfad zur JAR-Datei ein:");
                    String componentUrl = scanner.nextLine();

                    componentID = componentOperationExecutor
                            .execute(new AddComponentOperation(componentAssembler, componentName, componentUrl));
                    componentOperationExecutor.execute(new CreateInstanceOperation(componentAssembler, componentID));
                    System.out.println("\nKomponente hinzugefügt. \nDie ID ist: " + componentID + "\n");

                    System.out.println("==========================================");
                    break;
                case "2":
                    if (componentID == null) {
                        System.out.println("Es wurde noch keine Komponente hinzugefügt.");
                        break;
                    }
                    System.out.println("Welcher Prozess soll gestoppt werden? Geben Sie die ID an:");
                    String compID = scanner.nextLine();
                    System.out.println("==========================================");
                    componentOperationExecutor.execute(new StopInstanceOperation(componentAssembler, compID));
                    break;
                case "3":
                    if (componentID == null) {
                        System.out.println("Es wurde noch keine Komponente hinzugefügt.");
                        break;
                    }
                    System.out.println("Welcher Prozess soll gelöscht werden? Geben Sie die ID an:");
                    String compID2 = scanner.nextLine();
                    System.out.println("==========================================");
                    componentOperationExecutor.execute(new DeleteComponentOperation(componentAssembler, compID2));
                    componentID = null;
                    break;
                case "0":
                    if (componentID != null) {
                        componentOperationExecutor
                                .execute(new DeleteComponentOperation(componentAssembler, componentID));
                    }
                    System.out.println("Beende Programm...");
                    System.exit(0);
                case "help":
                    printMenuOptions();
                    break;
                default:
                    System.out.println("Ungültige Eingabe");
                    break;
            }
        }

    }

    private static void printMenuOptions() {
        System.out.println("\n===============================================");
        System.out.println("|                  LZU-Menü                   |");
        System.out.println("===============================================");
        System.out.println("| Option |              Beschreibung          |");
        System.out.println("|--------|------------------------------------|");
        System.out.println("|    1   | Komponente hinzufügen              |");
        System.out.println("|    2   | Instanz stoppen                    |");
        System.out.println("|    3   | Komponente löschen                 |");
        System.out.println("|    0   | Beenden                            |");
        System.out.println("|  help  | LZU Menu                           |");
        System.out.println("===============================================\n");

    }

}