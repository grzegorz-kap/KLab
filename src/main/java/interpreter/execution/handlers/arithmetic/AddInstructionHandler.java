package interpreter.execution.handlers.arithmetic;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.ArithmeticOperationsFactory;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.math.NumberScalar;
import interpreter.parsing.model.NumberType;

public class AddInstructionHandler extends AbstractInstructionHandler {

    private ArithmeticOperationsFactory arithmeticOperationsFactory;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        ObjectData b = executionContext.executionStackPop();
        ObjectData a = executionContext.executionStackPop();
        NumberType numberType = ((NumberScalar) a).getNumberType();
        ObjectData result = arithmeticOperationsFactory.getAdder(numberType).add(a, b);
        executionContext.executionStackPush(result);
        instructionPointer.increment();
    }

    public void setArithmeticOperationsFactory(ArithmeticOperationsFactory arithmeticOperationsFactory) {
        this.arithmeticOperationsFactory = arithmeticOperationsFactory;
    }
}
