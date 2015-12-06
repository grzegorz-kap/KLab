package interpreter.execution.handlers.operators;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.Negable;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NotInstructionHandler extends AbstractInstructionHandler {
	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.NOT;
	}

	@Override
	public void handle(InstructionPointer instructionPointer) {
		executionContext.executionStackPush(((Negable<?>)executionContext.executionStackPop()).negate());;
		instructionPointer.increment();
	}
}
