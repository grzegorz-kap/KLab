package com.klab.interpreter.execution.handlers.indexing;

import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.execution.handlers.AbstractInstructionHandler;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.types.IdentifierObject;
import com.klab.interpreter.types.Sizeable;
import com.klab.interpreter.types.scalar.NumberScalarFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCellInstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;
    private NumberScalarFactory numberScalarFactory;

    protected abstract long index(Sizeable sizeable);

    @Override
    public void handle(InstructionPointer instructionPointer) {
        IdentifierObject id = (IdentifierObject) instructionPointer.currentInstruction().getObjectData(0);
        Sizeable sizeable = (Sizeable) memorySpace.get(id.getAddress());
        executionContext.executionStackPush(numberScalarFactory.getDouble(index(sizeable)));
        instructionPointer.increment();
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    public void setNumberScalarFactory(NumberScalarFactory numberScalarFactory) {
        this.numberScalarFactory = numberScalarFactory;
    }
}
