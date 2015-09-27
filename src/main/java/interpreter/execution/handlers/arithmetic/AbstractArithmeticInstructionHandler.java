package interpreter.execution.handlers.arithmetic;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.factory.ArithmeticOperationsFactory;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.math.scalar.NumberObject;
import interpreter.parsing.model.NumberType;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractArithmeticInstructionHandler extends AbstractInstructionHandler {

    protected ArithmeticOperationsFactory arithmeticOperationsFactory;

    public NumberType numberType(ObjectData a, ObjectData b) {
        return ((NumberObject) a).getNumberType();
    }

    protected void handleTwoArguments(InstructionPointer instructionPointer) {
        ObjectData b = executionContext.executionStackPop();
        ObjectData a = executionContext.executionStackPop();
        ObjectData result = calculate(a, b);
        executionContext.executionStackPush(result);
        instructionPointer.increment();
    }

    protected ObjectData calculate(ObjectData a, ObjectData b) {
        throw new RuntimeException();
    }

    @Autowired
    public void setArithmeticOperationsFactory(ArithmeticOperationsFactory arithmeticOperationsFactory) {
        this.arithmeticOperationsFactory = arithmeticOperationsFactory;
    }
}
