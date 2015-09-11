package interpreter.execution.handlers.arithmetic;

import interpreter.commons.ObjectData;
import interpreter.execution.model.InstructionPointer;

public class SubInstructionHandler extends AbstractArithmeticInstructionHandler {
    @Override
    public void handle(InstructionPointer instructionPointer) {
        ObjectData b = executionContext.executionStackPop();
        ObjectData a = executionContext.executionStackPop();
        ObjectData result = arithmeticOperationsFactory.getSubtractor(numberType(a, b)).sub(a, b);
        executionContext.executionStackPush(result);
        instructionPointer.increment();
    }
}
