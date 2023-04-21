package org.bonn.lzu.Command;

import java.lang.reflect.InvocationTargetException;

import org.bonn.lzu.Classes.ComponentAssembler;

public class DeleteComponentOperation implements ComponentAssemblerOperation {
    private ComponentAssembler assembler;
    private String componentID;

    public DeleteComponentOperation(ComponentAssembler componentAssembler, String componentID) {
        this.assembler = componentAssembler;
        this.componentID = componentID;
    }

    @Override
    public String execute() throws ClassNotFoundException, IllegalAccessException, InstantiationException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException {
        assembler.deleteComponent(componentID);
        return "component deleted";
    }
}