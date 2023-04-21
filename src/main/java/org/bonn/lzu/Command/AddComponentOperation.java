package org.bonn.lzu.Command;

import java.io.IOException;

import org.bonn.lzu.Classes.ComponentAssembler;

class AddComponentOperation implements ComponentAssemblerOperation {
    private ComponentAssembler assembler;
    private String componentName;
    private String componentUrl;

    public AddComponentOperation(ComponentAssembler assembler, String componentName, String componentUrl) {
        this.assembler = assembler;
        this.componentName = componentName;
        this.componentUrl = componentUrl;
    }

    @Override
    public String execute() throws IOException, ClassNotFoundException {
        return assembler.addComponent(componentName, componentUrl);
    }
}
