package interpreter.execution.handlers.arithmetic;

import interpreter.commons.ObjectData;
import interpreter.execution.model.InstructionPointer;

public class SubInstructionHandler extends AbstractArithmeticInstructionHandler {

    @Override
    public void handle(InstructionPointer instructionPointer) {
        handleTwoArguments(instructionPointer);
    }

    @Override
    public ObjectData calculate(ObjectData a, ObjectData b) {
        return arithmeticOperationsFactory.getSubtractor(numberType(a, b)).sub(a, b);
    }
}
