package org.bonn.lzu.helper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarReader {
    private URLClassLoader cl;
    private JarFile jarFile;

    public JarReader(String pathToJar) throws IOException {
        URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
        this.cl = URLClassLoader.newInstance(urls);
        this.jarFile = new JarFile(pathToJar);
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return cl.loadClass(className);
    }

    public List<Class<?>> loadClassList() throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        Enumeration<JarEntry> e = jarFile.entries();
        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            Class<?> c = cl.loadClass(className);
            classes.add(c);
        }
        return classes;
    }

    public static List<Class<?>> JarClassLoader(String pathToJar) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(pathToJar);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            Class<?> c = cl.loadClass(className);
            classes.add(c);
        }
        return classes;
    }

    // get Manifest
    public String getMainClass() throws IOException {

        Manifest manifest = this.jarFile.getManifest();

        // Get the main attributes of the manifest
        Attributes mainAttributes = manifest.getMainAttributes();

        // Get the value of the Main-Class attribute
        String mainClass = mainAttributes.getValue(Attributes.Name.MAIN_CLASS);
        return mainClass;
    }
}
