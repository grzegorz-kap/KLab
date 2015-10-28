package interpreter.service.functions;

import static java.util.Objects.nonNull;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.execution.exception.UndefinedVariableException;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.service.functions.exception.UndefinedFunctionException;
import interpreter.service.functions.model.CallInstruction;
import interpreter.service.functions.model.InternalFunction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.ObjectData;

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
			throw new UndefinedFunctionException(instruction);
		}
	}

	private void handleInternalFunctionCall(InstructionPointer instructionPointer) {
		CallInstruction instruction =  (CallInstruction) instructionPointer.current();
		InternalFunction internalFunction = internalFunctionsHolder.get(instruction.getInternalFunctionAddress());
		ObjectData[] data = new ObjectData[instruction.getArgumentsNumber()];
		for(int index = instruction.getArgumentsNumber()-1 ; index >= 0 ; index --) {
			data[index] = executionContext.executionStackPop();
		}
		executionContext.executionStackPush(internalFunction.call(data));
		instructionPointer.increment();
	}

	private void handleVariableCall(InstructionPointer instructionPointer) {
		throw new RuntimeException("Variable function call not supported yet");
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
