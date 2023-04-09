package org.bonn.lzu.Classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComponentAssemblerTest {

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
        ComponentAssembler assembler = new ComponentAssembler();
        assembler.addComponent("component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");

        // Verify that the component was added
        assertEquals(1, assembler.componentClassLoaders.size());

        // print all Created Components
        assembler.listComponentsWithState(State.Created);

    }

    @Test
    void testCreateInstance() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        ComponentAssembler assembler = new ComponentAssembler();
        var cID = assembler.addComponent("component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");
        assembler.createInstance(cID);

        // assert that the component in the assembler is in the state "Started"
        assertEquals(State.STARTED, assembler.componentClassLoaders.get(cID).getState());
        

    }

    @Test
    void testListComponentsWithState() {

    }

    @Test
    void testStopInstance() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        ComponentAssembler assembler = new ComponentAssembler();
        var cID = assembler.addComponent("component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");
        assembler.createInstance(cID);
        assembler.stopInstance(cID);

        assertEquals(State.STOPPED, assembler.componentClassLoaders.get(cID).getState());

    }

    @Test
    void testParallelCreateInstance() throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {
        ComponentAssembler assembler = new ComponentAssembler();

        // Create a CountDownLatch to synchronize the execution of the threads
        CountDownLatch latch = new CountDownLatch(2);

        // Create two threads that will run the start method of the components
        Thread thread1 = new Thread(() -> {
            try {
                var c1ID = assembler.addComponent("component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");
                assembler.createInstance(c1ID);
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                var c2ID = assembler.addComponent("component", "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar");
                assembler.createInstance(c2ID);
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start the threads
        thread1.start();
        thread2.start();
        

        // Wait for both threads to finish
        latch.await();

        // sleep main thread for 1 second
        Thread.sleep(1000);
        
        // verify 2 components are in the Map
        assertEquals(2, assembler.componentClassLoaders.size());
        assembler.listComponentsWithState(State.STARTED);

    }
}
