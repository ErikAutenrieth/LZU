package org.bonn.lzu.Command;

import org.bonn.lzu.Classes.ComponentAssembler;

import java.io.IOException;

public class main {

    public static void main() throws IOException, ClassNotFoundException {


        ComponentAssembler assembler = new ComponentAssembler();
        String id = assembler.addComponent("myComponent", "http://mycomponent.com/mycomponent.jar");


        CreateInstanceCommand createInstanceCommand = new CreateInstanceCommand(assembler);
        createInstanceCommand.execute(id);

        StopInstanceCommand stopInstanceCommand = new StopInstanceCommand(assembler);
        stopInstanceCommand.execute(id);

        DeleteComponentCommand deleteComponentCommand = new DeleteComponentCommand(assembler);
        deleteComponentCommand.execute(id);

    }
}
