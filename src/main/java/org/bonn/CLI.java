package org.bonn;

import org.bonn.lzu.Classes.ComponentAssembler;
import org.bonn.lzu.Command.*;

import java.util.Scanner;

public class CLI {

    public static void main(String[] args) throws Exception {

        // Beispiel Pfad:
        // src/test/resources/classLoader/jarDir/Minimal_Komponent.jar

        ComponentOperationExecutor componentOperationExecutor = new ComponentOperationExecutor();
        ComponentAssembler componentAssembler = new ComponentAssembler();
        String componentID = null;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenuOptions();
            System.out.println("Ihre Eingabe:");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Bitte geben Sie den Namen der Komponente ein:");
                    String componentName = scanner.nextLine();
                    System.out.println("Bitte geben Sie den Pfad zur JAR-Datei ein:");
                    String componentUrl = scanner.nextLine();

                    componentID = componentOperationExecutor.execute(new AddComponentOperation(componentAssembler, componentName, componentUrl));
                    componentOperationExecutor.execute(new CreateInstanceOperation(componentAssembler, componentID));
                    System.out.println("Komponente hinzugefügt. Die ID ist: " + componentID);
                    break;
                case "2":
                    if (componentID == null) {
                        System.out.println("Es wurde noch keine Komponente hinzugefügt.");
                        break;
                    }
                    System.out.println("Welcher Prozess soll gestoppt werden? Geben Sie die ID an:");
                    String compID = scanner.nextLine();
                    componentOperationExecutor.execute(new StopInstanceOperation(componentAssembler, compID));
                    break;
                case "3":
                    if (componentID == null) {
                        System.out.println("Es wurde noch keine Komponente hinzugefügt.");
                        break;
                    }
                    System.out.println("Welcher Prozess soll gelöscht werden? Geben Sie die ID an:");
                    String compID2 = scanner.nextLine();
                    componentOperationExecutor.execute(new DeleteComponentOperation(componentAssembler, compID2));
                    break;
                case "0":
                    if (componentID != null) {
                        componentOperationExecutor.execute(new DeleteComponentOperation(componentAssembler, componentID));
                    }
                    System.out.println("Beende Programm...");
                    System.exit(0);
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
        System.out.println("===============================================\n");

    }

}