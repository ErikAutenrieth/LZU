package org.bonn.lzu.helper;

import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class JarReaderTest {

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


    /**
     * Tests the JarClassLoader method of the JarReader class.
     *
     * @throws IOException if an I/O error occurs while reading the JAR file
     * @throws ClassNotFoundException if a class cannot be found
     */
    @org.junit.jupiter.api.Test
    void jarClassLoaderStatic() throws IOException, ClassNotFoundException {
        // Set the expected class name and path to the JAR file
        String className = "org.bonn.ooka.buchungssystem.ss2022";
        String pathToJar = "src/test/resources/classLoader/jarDir/codesOOKA-1.0-SNAPSHOT.jar";

        // Load classes from the JAR file
        List<Class<?>> loadedClass = JarReader.JarClassLoader(pathToJar);

        // print name of all loadedClasses
        // loadedClass.forEach(c -> System.out.println(c.getName()));

        // Assert that the expected class is in the list of loaded classes
        assertTrue(loadedClass.stream().anyMatch(c -> c.getName().equals(className + ".Hotel")));
    }

    @org.junit.jupiter.api.Test
    void testClassLoaderIsolation() throws Exception {

        String className = "org.bonn.ooka.buchungssystem.ss2022";
        String pathToJar = "src/test/resources/classLoader/jarDir/codesOOKA-1.0-SNAPSHOT.jar";

        // Set the paths to the JAR files for each component
        String pathToJar1 = "src/test/resources/classLoader/jarDir/codesOOKA-1.0-SNAPSHOT.jar";
        String pathToJar2 = "src/test/resources/classLoader/jarDir/codesOOKA-1.0-SNAPSHOT.jar";

        // Create two instances of your class loader, each configured to load classes from a different component
        JarReader loader1 = new JarReader(pathToJar1);
        JarReader loader2 = new JarReader(pathToJar2);

        // Load the same class from each component using the respective class loader
        Class<?> class1 = loader1.loadClass(className + ".Hotel");
        Class<?> class2 = loader2.loadClass(className + ".Hotel");

        // Verify that the loaded classes are distinct and not equal
        assertNotEquals(class1, class2);
    }

    // test minimal setup with Minimal_Komponent
    @org.junit.jupiter.api.Test
    void testMinimalSetup() throws Exception {
        String className = "org.example";
        String pathToJar = "src/test/resources/classLoader/jarDir/Minimal_Komponent.jar";

        // Create two instances of your class loader, each configured to load classes from a different component
        JarReader loader = new JarReader(pathToJar);

        // Load the same class from each component using the respective class loader
        List<Class<?>> classList = loader.loadClassList();

        // print name of all loadedClasses
        System.out.println("Loaded classes:");
        classList.forEach(c -> System.out.println(c.getName()));
        Class<?> init_class = classList.get(0);

        // execute first class of class
        Object init_class_object = init_class.getDeclaredConstructor().newInstance();
        init_class.getMethod("start").invoke(init_class_object);
        init_class.getMethod("stop").invoke(init_class_object);

    }

    @org.junit.jupiter.api.Test
    void testUebung1Setup() throws Exception {
        String className = "org.bonn.ooka.buchungssystem.ss2022";
        String pathToJar = "src/test/resources/classLoader/jarDir/codesOOKA-1.0-SNAPSHOT.jar";

        // Create two instances of your class loader, each configured to load classes from a different component
        JarReader loader = new JarReader(pathToJar);

        // Load the same class from each component using the respective class loader
        List<Class<?>> classList = loader.loadClassList();

        // read main class
        String mainClassName = loader.getMainClass();

        // load main class
        Class<?> init_class = loader.loadClass(mainClassName);

        // print name of all loadedClasses
        System.out.println("Loaded classes:");
        classList.forEach(c -> System.out.println(c.getName()));

        // execute first class of class
        Object init_class_object = init_class.getDeclaredConstructor().newInstance();
        init_class.getMethod("start").invoke(init_class_object);
        init_class.getMethod("stop").invoke(init_class_object);

    }

}