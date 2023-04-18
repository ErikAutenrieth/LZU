package org.bonn.lzu.Command;

import org.bonn.lzu.Classes.ComponentAssembler;

public class StopInstanceCommand extends ComponentCommand {
    public StopInstanceCommand(ComponentAssembler assembler) {
        super(assembler);
    }

    @Override
    public void execute(String id) {
        try {
            assembler.stopInstance(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
