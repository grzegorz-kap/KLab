package interpreter.execution.handlers.indexing;

import interpreter.commons.MemorySpace;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.types.IdentifierObject;
import interpreter.types.Sizeable;
import interpreter.types.scalar.NumberScalarFactory;
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
