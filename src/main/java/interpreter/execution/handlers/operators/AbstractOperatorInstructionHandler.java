package interpreter.execution.handlers.operators;

import interpreter.core.arithmetic.factory.OperatorExecutionFactory;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.helper.NumericUtils;
import interpreter.execution.model.InstructionPointer;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.converters.ConvertersHolder;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractOperatorInstructionHandler extends AbstractInstructionHandler {

    protected OperatorExecutionFactory operatorExecutionFactory;
    private NumericUtils numericUtils;
    private ConvertersHolder convertersHolder;

    public NumericObject convert(NumericObject data, NumericType destType) {
        return convertersHolder.getConverter(data.getNumericType(), destType).convert(data);
    }

    protected void handleTwoArguments(InstructionPointer instructionPointer) {
        NumericObject b = (NumericObject) executionContext.executionStackPop();
        NumericObject a = ((NumericObject) executionContext.executionStackPop());
        NumericType numericType = numberType(a, b);
        ObjectData result = calculate(a, b, numericType);
        executionContext.executionStackPush(result);
        instructionPointer.increment();
    }

    protected ObjectData calculate(NumericObject a, NumericObject b, NumericType type) {
        throw new UnsupportedOperationException();
    }

    private NumericType numberType(ObjectData a, ObjectData b) {
        return numericUtils.resolveType(((NumericObject) a), ((NumericObject) b));
    }


    @Autowired
    public void setOperatorExecutionFactory(OperatorExecutionFactory operatorExecutionFactory) {
        this.operatorExecutionFactory = operatorExecutionFactory;
    }

    @Autowired
    public void setNumericUtils(NumericUtils numericUtils) {
        this.numericUtils = numericUtils;
    }

    @Autowired
    public void setConvertersHolder(ConvertersHolder convertersHolder) {
        this.convertersHolder = convertersHolder;
    }
}
