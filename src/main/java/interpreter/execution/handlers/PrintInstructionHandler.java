package interpreter.execution.handlers;

import interpreter.core.events.PrintEvent;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PrintInstructionHandler extends AbstractInstructionHandler implements InstructionHandler {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        publishPrintEvent();
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.PRINT;
    }

    private void publishPrintEvent() {
        PrintEvent printEvent = new PrintEvent(this);
        printEvent.setObjectData(executionContext.executionStackPop());
        applicationEventPublisher.publishEvent(printEvent);
    }

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
