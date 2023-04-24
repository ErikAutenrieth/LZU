package org.bonn.lzu.Command;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.bonn.lzu.Classes.ComponentAssembler;
import org.bonn.lzu.Classes.Component.State;
import org.bonn.lzu.Command.DeleteComponentOperation;
import org.bonn.lzu.Command.StopInstanceOperation;
import org.bonn.lzu.Command.AddComponentOperation;
import org.bonn.lzu.Command.CreateInstanceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {
    private ComponentAssembler componentAssembler;
    private AddComponentOperation addComponentOperation;
    private CreateInstanceOperation createInstanceOperation;
    private DeleteComponentOperation deleteComponentOperation;
    private StopInstanceOperation stopInstanceOperation;

    @BeforeEach
    void setUp() {
        // list of all paths to the JAR files
        String[] jarPaths = {
                "src/test/resources/classLoader/jarDir/codesOOKA-1.0-SNAPSHOT.jar",
                "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar"
        };

        // test if all JAR files exist
        for (String path : jarPaths) {
            File file = new File(path);
            assertTrue(file.exists());
        }

    }


    @Test
    void testAddComponent() throws ClassNotFoundException, IOException {
        componentAssembler = new ComponentAssembler();
        addComponentOperation = new AddComponentOperation(componentAssembler, "component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");
        String componentID = addComponentOperation.execute();

        // Verify that the component was added
        assertEquals(1, componentAssembler.getComponentClassLoaders().size());

        // print all Created Components
        componentAssembler.listComponentsWithState(State.Created);
    }

    @Test
    void testCreateInstance() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        componentAssembler = new ComponentAssembler();
        addComponentOperation = new AddComponentOperation(componentAssembler, "component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");
        String componentID = addComponentOperation.execute();
        createInstanceOperation = new CreateInstanceOperation(componentAssembler, componentID);
        createInstanceOperation.execute();

        // assert that the component in the assembler is in the state "Started"
        assertEquals(State.STARTED, componentAssembler.getComponentClassLoaders().get(componentID).getState());
    }

    @Test
    void testStopInstance() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {
        componentAssembler = new ComponentAssembler();
        addComponentOperation = new AddComponentOperation(componentAssembler, "component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");
        String componentID = addComponentOperation.execute();
        createInstanceOperation = new CreateInstanceOperation(componentAssembler, componentID);
        createInstanceOperation.execute();
        stopInstanceOperation = new StopInstanceOperation(componentAssembler, componentID);
        stopInstanceOperation.execute();

        // assert that the component in the assembler is in the state "Stopped"
        assertEquals(State.STOPPED, componentAssembler.getComponentClassLoaders().get(componentID).getState());
    }

    @Test
    void testDeleteComponent() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {

        componentAssembler = new ComponentAssembler();
        addComponentOperation = new AddComponentOperation(componentAssembler, "component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");

        String componentID = addComponentOperation.execute();

        createInstanceOperation = new CreateInstanceOperation(componentAssembler, componentID);
        createInstanceOperation.execute();

        Thread.sleep(2000);

        stopInstanceOperation = new StopInstanceOperation(componentAssembler, componentID);
        stopInstanceOperation.execute();

        Thread.sleep(2000);

        deleteComponentOperation = new DeleteComponentOperation(componentAssembler, componentID);
        deleteComponentOperation.execute();


        assertNull(componentAssembler.getComponentClassLoaders().get(componentID));
    }



    @Test
    void testMultipleDeleteComponents() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException{
        componentAssembler = new ComponentAssembler();
        addComponentOperation = new AddComponentOperation(componentAssembler, "component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");
        addComponentOperation = new AddComponentOperation(componentAssembler, "component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");

        String componentID = addComponentOperation.execute();
        String componentID2 = addComponentOperation.execute();

        createInstanceOperation = new CreateInstanceOperation(componentAssembler, componentID);
        createInstanceOperation.execute();
        createInstanceOperation = new CreateInstanceOperation(componentAssembler, componentID2);
        createInstanceOperation.execute();

        Thread.sleep(2000);

        stopInstanceOperation = new StopInstanceOperation(componentAssembler, componentID);
        stopInstanceOperation.execute();
        stopInstanceOperation = new StopInstanceOperation(componentAssembler, componentID2);
        stopInstanceOperation.execute();

        Thread.sleep(2000);

        deleteComponentOperation = new DeleteComponentOperation(componentAssembler, componentID);
        deleteComponentOperation.execute();
        deleteComponentOperation = new DeleteComponentOperation(componentAssembler, componentID2);
        deleteComponentOperation.execute();

        assertNull(componentAssembler.getComponentClassLoaders().get(componentID));
        assertNull(componentAssembler.getComponentClassLoaders().get(componentID2));

    }

}
