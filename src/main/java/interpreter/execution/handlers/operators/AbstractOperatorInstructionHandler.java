package interpreter.execution.handlers.operators;

import interpreter.core.arithmetic.factory.OperatorExecutionFactory;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.parsing.model.NumericType;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractOperatorInstructionHandler extends AbstractInstructionHandler {

    protected OperatorExecutionFactory operatorExecutionFactory;

    public NumericType numberType(ObjectData a, ObjectData b) {
        return ((NumericObject) a).getNumericType();
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
    public void setOperatorExecutionFactory(OperatorExecutionFactory operatorExecutionFactory) {
        this.operatorExecutionFactory = operatorExecutionFactory;
    }
}
