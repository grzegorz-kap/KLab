package interpreter.execution.handlers;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.translate.model.JumperInstruction;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMPFNPInstructionHandler extends AbstractInstructionHandler {
	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.JMPFNP;
	}

	@Override
	public void handle(InstructionPointer instructionPointer) {
		JumperInstruction jmp = (JumperInstruction) instructionPointer.current();
		boolean value = executionContext.executionStackPeek().isTrue();
		if(!value) {
			instructionPointer.jumpTo(jmp.getJumpIndex());
		} else {
			instructionPointer.increment();
		}
	}
}
