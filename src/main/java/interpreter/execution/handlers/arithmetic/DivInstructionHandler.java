package interpreter.execution.handlers.arithmetic;

import interpreter.commons.ObjectData;
import interpreter.execution.model.InstructionPointer;

public class DivInstructionHandler extends AbstractArithmeticInstructionHandler {

    @Override
    public void handle(InstructionPointer instructionPointer) {
        handleTwoArguments(instructionPointer);
    }

    @Override
    public ObjectData calculate(ObjectData a, ObjectData b) {
        return arithmeticOperationsFactory.getDivider(numberType(a, b)).div(a, b);
    }
}
