package com.klab.interpreter.execution.handlers;

import com.klab.common.EventService;
import com.klab.interpreter.core.events.PrintEvent;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PrintInstructionHandler extends AbstractInstructionHandler implements InstructionHandler {
    private EventService eventService;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        // TODO script and functions call (can return void)
        if (executionContext.executionStackSize() > 0) {
            eventService.publish(new PrintEvent(executionContext.executionStackPop(), this));
        }
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.PRINT;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
