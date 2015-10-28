package interpreter.service.functions;

import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.service.functions.model.CallInstruction;
import interpreter.translate.model.InstructionCode;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallInstructionHandler extends AbstractInstructionHandler {

	private InternalFunctionsHolder internalFunctionsHolder;

	@Override
	public void handle(InstructionPointer instructionPointer) {
		CallInstruction instruction = (CallInstruction) instructionPointer.current();
		if (nonNull(instruction.getVariableAddress())) {
			handleVariableCall(instructionPointer);
		} else if (nonNull(instruction.getInternalFunctionAddress())) {
			handleInternalFunctionCall(instructionPointer);
		} else {
			throw new RuntimeException();
		}
	}

	private void handleInternalFunctionCall(InstructionPointer instructionPointer) {
		throw new RuntimeException();

	}

	private void handleVariableCall(InstructionPointer instructionPointer) {
		throw new RuntimeException();
	}

	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.CALL;
	}

	@Autowired
	public void setInternalFunctionsHolder(InternalFunctionsHolder internalFunctionsHolder) {
		this.internalFunctionsHolder = internalFunctionsHolder;
	}
}
