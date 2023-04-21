package org.bonn.lzu.Command;

import java.lang.reflect.InvocationTargetException;

import org.bonn.lzu.Classes.ComponentAssembler;

class CreateInstanceOperation implements ComponentAssemblerOperation {
    private ComponentAssembler assembler;
    private String componentID;

    public CreateInstanceOperation(ComponentAssembler assembler, String componentID) {
        this.assembler = assembler;
        this.componentID = componentID;
    }

    @Override
    public String execute() throws ClassNotFoundException, IllegalAccessException, InstantiationException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        assembler.createInstance(componentID);
        return "Instance created";
    }
}
