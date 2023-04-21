import org.bonn.lzu.Classes.ComponentAssembler;
import org.bonn.lzu.Command.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.*;

public class CLI2 implements Callable<Void> {

    // Beispieleingabe:
    // java -jar LZU.jar --add --name "new_component" --url "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar"

    @Option(names = { "-a", "--add" }, description = "Add a component")
    private boolean addComponent;

    @Option(names = { "-n", "--name" }, description = "Component name")
    private String componentName;

    @Option(names = { "-u", "--url" }, description = "Component JAR file URL")
    private String componentUrl;

    @Option(names = { "-s", "--stop" }, description = "Stop an instance")
    private boolean stopInstance;

    @Option(names = { "-i", "--instance" }, description = "Instance ID")
    private String instanceId;

    @Option(names = { "-d", "--delete" }, description = "Delete a component")
    private boolean deleteComponent;

    private ComponentOperationExecutor componentOperationExecutor;
    private ComponentAssembler componentAssembler;
    private String componentID = null;

    public CLI2(ComponentOperationExecutor componentOperationExecutor, ComponentAssembler componentAssembler) {
        this.componentOperationExecutor = componentOperationExecutor;
        this.componentAssembler = componentAssembler;
    }

    public static void main(String[] args) throws Exception {
        CLI2 cli = new CLI2(new ComponentOperationExecutor(), new ComponentAssembler());
        CommandLine.call(cli, args);
    }

    @Override
    public Void call() throws Exception {
        if (addComponent) {
            componentID = componentOperationExecutor.execute(new AddComponentOperation(componentAssembler, componentName, componentUrl));
            componentOperationExecutor.execute(new CreateInstanceOperation(componentAssembler, componentID));
            System.out.println("Komponente hinzugefügt. Die ID ist: " + componentID);
        } else if (stopInstance) {
            if (componentID == null) {
                System.out.println("Es wurde noch keine Komponente hinzugefügt.");
            } else {
                componentOperationExecutor.execute(new StopInstanceOperation(componentAssembler, instanceId));
            }
        } else if (deleteComponent) {
            if (componentID == null) {
                System.out.println("Es wurde noch keine Komponente hinzugefügt.");
            } else {
                componentOperationExecutor.execute(new DeleteComponentOperation(componentAssembler, componentID));
            }
        } else {
            System.out.println("Bitte wählen Sie eine Option:");
            System.out.println("  -a, --add          Komponente hinzufügen");
            System.out.println("  -s, --stop         Instanz stoppen");
            System.out.println("  -d, --delete       Komponente löschen");
            System.out.println("Beenden: ctrl-c");
        }
        return null;
    }
}
