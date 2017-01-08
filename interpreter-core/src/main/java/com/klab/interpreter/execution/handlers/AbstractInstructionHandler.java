package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.execution.model.ExecutionContext;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.scalar.Scalar;

public abstract class AbstractInstructionHandler implements InstructionHandler {

    protected ExecutionContext executionContext;

    public abstract void handle(InstructionPointer instructionPointer);

    @Override
    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    protected Number getNumber(ObjectData objectData) {
        if (objectData instanceof Scalar) {
            return ((Scalar<?>) objectData).getValue();
        }
        if (objectData instanceof Matrix) {
            Matrix<?> matrix = (Matrix<?>) objectData;
            if (!matrix.isScalar()) {
                throw new RuntimeException();
            }
            return matrix.get(0);
        }
        throw new RuntimeException();
    }
}
