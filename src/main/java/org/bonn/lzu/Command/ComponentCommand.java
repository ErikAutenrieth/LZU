package org.bonn.lzu.Command;

import org.bonn.lzu.Classes.ComponentAssembler;

import java.awt.*;

public abstract class ComponentCommand implements Command {
    protected final ComponentAssembler assembler;

    public ComponentCommand(ComponentAssembler assembler) {
        this.assembler = assembler;
    }

    public abstract void execute(String id);
}
