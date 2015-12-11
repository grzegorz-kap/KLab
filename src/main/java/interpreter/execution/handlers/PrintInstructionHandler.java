package interpreter.execution.handlers;

import com.google.common.eventbus.EventBus;
import interpreter.core.events.PrintEvent;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PrintInstructionHandler extends AbstractInstructionHandler implements InstructionHandler {
    private EventBus eventBus;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        eventBus.post(new PrintEvent(executionContext.executionStackPop(), this));
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.PRINT;
    }

    @Autowired
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
