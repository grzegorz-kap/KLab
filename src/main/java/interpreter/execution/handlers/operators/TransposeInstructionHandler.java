package interpreter.execution.handlers.operators;

import interpreter.core.arithmetic.factory.NumericObjectsOperatorFactory;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.NumericObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransposeInstructionHandler extends AbstractInstructionHandler {
	private NumericObjectsOperatorFactory operatorFactory;

	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.TRANSPOSE;
	}

	@Override
	public void handle(InstructionPointer instructionPointer) {
		NumericObject a = (NumericObject) executionContext.executionStackPop();
		executionContext.executionStackPush(operatorFactory.getOperator(a.getNumericType()).transpose(a));
		instructionPointer.increment();
	}

	@Autowired
	public void setOperatorFactory(NumericObjectsOperatorFactory operatorFactory) {
		this.operatorFactory = operatorFactory;
	}
}
