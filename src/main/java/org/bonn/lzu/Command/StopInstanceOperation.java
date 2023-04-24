package org.bonn.lzu.Command;

import java.lang.reflect.InvocationTargetException;

import org.bonn.lzu.Classes.ComponentAssembler;

public class StopInstanceOperation implements ComponentAssemblerOperation {
    private ComponentAssembler assembler;
    private String componentID;

    public StopInstanceOperation(ComponentAssembler componentAssembler, String componentID) {
        this.assembler = componentAssembler;
        this.componentID = componentID;
    }

    @Override
    public String execute() throws ClassNotFoundException, IllegalAccessException, InstantiationException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {
        assembler.stopInstance(componentID);
        return "Instance created";
    }
}
