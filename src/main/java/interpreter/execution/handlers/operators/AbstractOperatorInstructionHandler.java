package interpreter.execution.handlers.operators;

import interpreter.core.arithmetic.factory.NumericObjectsOperatorFactory;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.helper.NumericUtils;
import interpreter.execution.model.InstructionPointer;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.converters.ConvertersHolder;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractOperatorInstructionHandler extends AbstractInstructionHandler {
	protected NumericObjectsOperatorFactory operatorFactory;
	private NumericUtils numericUtils;
	private ConvertersHolder convertersHolder;

	@Override
	public void handle(InstructionPointer instructionPointer) {
		handleTwoArguments(instructionPointer);
	}

	public NumericObject convert(NumericObject data, NumericType destType) {
		if (destType.equals(data.getNumericType())) {
			return data;
		}
		return convertersHolder.getConverter(data.getNumericType(), destType).convert(data);
	}

	protected void handleTwoArguments(InstructionPointer instructionPointer) {
		NumericObject b = (NumericObject) executionContext.executionStackPop();
		NumericObject a = ((NumericObject) executionContext.executionStackPop());
		NumericType numericType = numberType(a, b);
		NumericObject result = calculate(a, b, numericType);
		result.setNumericType(numericType);
		executionContext.executionStackPush(result);
		instructionPointer.increment();
	}

	protected abstract NumericObject calculate(NumericObject a, NumericObject b, NumericType type);

	private NumericType numberType(ObjectData a, ObjectData b) {
		return numericUtils.resolveType(((NumericObject) a), ((NumericObject) b));
	}

	@Autowired
	public void setNumericObjectsOperatorFactory(NumericObjectsOperatorFactory numericObjectsOperatorFactory) {
		this.operatorFactory = numericObjectsOperatorFactory;
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
