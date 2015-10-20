package interpreter.execution.handlers;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.InstructionCode;
import interpreter.translate.model.instruction.JumperInstruction;
import interpreter.types.ObjectData;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMPFInstructionHandler extends AbstractInstructionHandler {

	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.JMPF;
	}

	@Override
	public void handle(InstructionPointer instructionPointer) {
		ObjectData objectData = executionContext.executionStackPop();
		if (!objectData.isTrue()) {
			JumperInstruction jumperInstruction = (JumperInstruction) instructionPointer.current();
			instructionPointer.jumpTo(jumperInstruction.getJumpIndex());
		} else {
			instructionPointer.increment();
		}
	}
}
