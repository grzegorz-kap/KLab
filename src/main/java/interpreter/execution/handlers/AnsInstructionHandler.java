package interpreter.execution.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.commons.IdentifierMapper;
import interpreter.commons.MemorySpace;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.ObjectData;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AnsInstructionHandler extends AbstractInstructionHandler {
	
	private Integer ansAddress;
	private MemorySpace memorySpace;

	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.ANS;
	}

	@Override
	public void handle(InstructionPointer instructionPointer) {
		if(executionContext.executionStackSize()>0) {
			ObjectData data = executionContext.executionStackPop();
			data.setName("ans");
			memorySpace.set(ansAddress, data);
			executionContext.executionStackPush(data);
		}
		instructionPointer.increment();
	}
	
	@Autowired
	private void setIndentifierMapper(IdentifierMapper identifierMapper) {
		ansAddress = identifierMapper.registerMainIdentifier("ans");
	}

	@Autowired
	public void setMemorySpace(MemorySpace memorySpace) {
		this.memorySpace = memorySpace;
	}

}
