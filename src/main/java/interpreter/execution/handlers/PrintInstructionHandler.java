package interpreter.execution.handlers;

import interpreter.core.events.PrintEvent;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PrintInstructionHandler extends AbstractInstructionHandler implements InstructionHandler {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    private void init() {
        supportedInstructionCode = InstructionCode.PRINT;
    }

    @Override
    public void handle(InstructionPointer instructionPointer) {
        publishPrintEvent();
        instructionPointer.increment();
    }

    private void publishPrintEvent() {
        PrintEvent printEvent = new PrintEvent(this);
        printEvent.setObjectData(executionContext.executionStackPop());
        applicationEventPublisher.publishEvent(printEvent);
    }
}
