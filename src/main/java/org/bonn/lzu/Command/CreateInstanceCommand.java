package org.bonn.lzu.Command;

import org.bonn.lzu.Classes.ComponentAssembler;

import java.awt.*;

public class CreateInstanceCommand extends ComponentCommand {
    public CreateInstanceCommand(ComponentAssembler assembler) {
        super(assembler);
    }

    @Override
    public void execute(String id) {
        try {
            assembler.createInstance(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}