package org.bonn.lzu.Command;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

import org.bonn.lzu.Classes.ComponentAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandTest {

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
    void testCreateInstanceCommand() throws IOException, ClassNotFoundException {

        ComponentAssembler assembler = new ComponentAssembler();
        String id = assembler.addComponent("myComponent", "src/test/resources/classLoader/jarDir/codesOOKA-1.0-SNAPSHOT.jar");

        CreateInstanceCommand createInstanceCommand = new CreateInstanceCommand(assembler);
        createInstanceCommand.execute(id);
    }




}
