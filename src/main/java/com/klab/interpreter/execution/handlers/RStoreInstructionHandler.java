package com.klab.interpreter.execution.handlers;

import com.klab.common.EventService;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.core.events.PrintEvent;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.translate.model.ReverseStoreInstruction;
import com.klab.interpreter.types.IdentifierObject;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RStoreInstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;
    private EventService eventService;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        // TODO internal function return
        ReverseStoreInstruction instruction = (ReverseStoreInstruction) instructionPointer.currentInstruction();
        IdentifierObject target = (IdentifierObject) executionContext.executionStackPop();
        ObjectData source = memorySpace.set(target.getAddress(), executionContext.executionStackPop(), target.getName());
        if (instruction.isPrint()) {
            eventService.publish(new PrintEvent(source, this));
        }
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.REVERSE_STORE;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
