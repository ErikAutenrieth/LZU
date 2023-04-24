package org.bonn.lzu.Command;

import java.util.ArrayList;
import java.util.List;

public class ComponentOperationExecutor {
    private final List<ComponentAssemblerOperation> operations = new ArrayList<>();

    public String execute(ComponentAssemblerOperation operation) throws Exception {
        operations.add(operation);
        return operation.execute();
    }

}
