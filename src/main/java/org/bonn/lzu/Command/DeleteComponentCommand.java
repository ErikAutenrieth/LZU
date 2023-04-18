package org.bonn.lzu.Command;

import org.bonn.lzu.Classes.ComponentAssembler;

public class DeleteComponentCommand extends ComponentCommand{


    public DeleteComponentCommand(ComponentAssembler assembler){
        super(assembler);
    }

    @Override
    public void execute(String id) {
        try {
            this.assembler.deleteComponent(id);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
