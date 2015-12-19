package interpreter.execution.handlers;

import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.scalar.NumberScalarFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LogicalInstructionHandler extends AbstractInstructionHandler {
	private NumberScalarFactory numberScalarFactory;
	
	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.LOGICAL;
	}

	@Override
	public void handle(InstructionPointer instructionPointer) {
		boolean logicalValue = executionContext.executionStackPeek().isTrue();
		executionContext.executionStackPush(numberScalarFactory.getDouble(logicalValue));
		instructionPointer.increment();
	}
	
	@Autowired
	public void setNumberScalarFactory(NumberScalarFactory numberScalarFactory) {
		this.numberScalarFactory = numberScalarFactory;
	}
}
