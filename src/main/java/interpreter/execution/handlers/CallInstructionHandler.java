package interpreter.execution.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.commons.IdentifierMapper;
import interpreter.core.internal.function.InternalFunction;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallInstructionHandler extends AbstractInstructionHandler {

	private List<InternalFunction> internalFunctions;

	@Autowired
	public CallInstructionHandler(IdentifierMapper identifierMapper, List<InternalFunction> internalFunctions) {
		this.internalFunctions = new ArrayList<>(Collections.nCopies(internalFunctions.size(), null));
		internalFunctions.forEach(function -> {
			Integer address = identifierMapper.registerInternalFunction(function.getName(),
					function.getArgumentsNumber());
			this.internalFunctions.set(address, function);
		});
	}

	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.CALL;
	}

	@Override
	public void handle(InstructionPointer instructionPointer) {
		throw new RuntimeException();
	}

}
