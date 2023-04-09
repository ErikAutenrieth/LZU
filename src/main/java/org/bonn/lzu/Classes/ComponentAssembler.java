package org.bonn.lzu.Classes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bonn.lzu.helper.JarReader;

public class ComponentAssembler {

    final protected Map<String, Component> componentClassLoaders = new ConcurrentHashMap<String, Component>();

    public String addComponent(String componentName, String componentUrl) throws IOException, ClassNotFoundException {



        // Create a new class loader for the component
        JarReader classLoader = new JarReader(componentUrl);
        String mainClass = classLoader.getMainClass();
        Class<?> mainClassObject = classLoader.loadClass(mainClass);

        int classLoaderSize = componentClassLoaders.size() + 1;
        String componentID = componentName + "-" + classLoaderSize;
        // Create a new component object
        Component component = new Component(classLoaderSize, componentName, mainClass, mainClassObject,
                State.Created);

        // Store the class loader in the map
        componentClassLoaders.put(componentID, component);
        return componentID;
    }

    public void createInstance(String componentID)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {

        Component component = componentClassLoaders.get(componentID);
        Class<?> init_class = component.mainClassObject();
        Object init_object = init_class.getDeclaredConstructor().newInstance();

        // Create a new thread to run the start method of the component
        Thread thread = new Thread(() -> {
            try {
                init_class.getMethod("start").invoke(init_object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();

        component.setState(State.STARTED);

    }

    public void stopInstance(String componentID)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {

        Component component = componentClassLoaders.get(componentID);
        Class<?> init_class = component.mainClassObject();
        Object init_object = init_class.getDeclaredConstructor().newInstance();

        init_class.getMethod("stop").invoke(init_object);

        component.setState(State.STOPPED);

    }

    public void listComponentsWithState(State state) {
        for (Map.Entry<String, Component> entry : componentClassLoaders.entrySet()) {
            if (entry.getValue().getState() == state) {
                System.out
                        .println(entry.getKey() + " " + entry.getValue().getName() + " " + entry.getValue().getState());
            }
        }
    }

}
