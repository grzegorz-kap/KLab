package com.klab.interpreter.service.functions;

import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.commons.exception.InterpreterException;
import com.klab.interpreter.execution.handlers.AbstractInstructionHandler;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.service.functions.model.CallInstruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.AddressIterator;
import com.klab.interpreter.types.Addressable;
import com.klab.interpreter.types.Indexable;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VariableFunctionCallHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        CallInstruction callInstruction = (CallInstruction) instructionPointer.currentInstruction();
        Indexable indexable = (Indexable) memorySpace.get(callInstruction.getVariableAddress());
        if (callInstruction.getArgumentsNumber() == 2) {
            handleTwo(indexable);
        } else if (callInstruction.getArgumentsNumber() == 1) {
            handleOne(indexable);
        } else {
            throw new InterpreterException();
        }
        instructionPointer.increment();
    }

    private void handleOne(Indexable indexable) {
        AddressIterator cell = ((Addressable) executionContext.executionStackPop()).getAddressIterator();
        executionContext.executionStackPush(indexable.get(cell));
    }

    private void handleTwo(Indexable indexable) {
        // TODO type check
        AddressIterator column = ((Addressable) executionContext.executionStackPop()).getAddressIterator();
        AddressIterator row = ((Addressable) executionContext.executionStackPop()).getAddressIterator();
        ObjectData objectData = indexable.get(row, column);
        executionContext.executionStackPush(objectData);
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return null;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
