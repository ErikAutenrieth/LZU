package org.bonn.lzu.Classes;

public class Component {
    private int id;
    private String name;
    private String mainClass;
    private Class<?> mainClassObject;
    private Thread thread = null;
    private State state;
    public enum State {
        STARTED,
        STOPPED,
        Created
    }

    public Component(int id, String name, String mainClass, Class<?> mainClassObject, State state) {
        this.id = id;
        this.name = name;
        this.mainClass = mainClass;
        this.mainClassObject = mainClassObject;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMainClass() {
        return mainClass;
    }

    public Class<?> mainClassObject() {
        return mainClassObject;
    }

    public State getState() {
        return state;
    }

    public void stopThread() {
        thread.interrupt();
        thread = null;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addThread(Thread thread) {
        this.thread = thread;
    }
}
