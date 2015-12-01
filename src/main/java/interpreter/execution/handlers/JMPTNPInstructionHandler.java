package interpreter.execution.handlers;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.translate.model.JumperInstruction;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMPTNPInstructionHandler extends AbstractInstructionHandler {
	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.JMPTNP;
	}

	@Override
	public void handle(InstructionPointer instructionPointer) {
		JumperInstruction jmp = (JumperInstruction) instructionPointer.current();
		boolean result = executionContext.executionStackPeek().isTrue();
		if (result) {
			instructionPointer.jumpTo(jmp.getJumpIndex());
		} else {
			instructionPointer.increment();
		}
	}
}
