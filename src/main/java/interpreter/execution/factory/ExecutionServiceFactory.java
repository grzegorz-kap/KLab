package interpreter.execution.factory;

import interpreter.core.arithmetic.ArithmeticOperationsFactory;
import interpreter.execution.handlers.PushInstructionHandler;
import interpreter.execution.handlers.arithmetic.AddInstructionHandler;
import interpreter.execution.model.ExecutionContext;
import interpreter.execution.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static interpreter.translate.model.instruction.InstructionCode.ADD;
import static interpreter.translate.model.instruction.InstructionCode.PUSH;

@Configuration
public class ExecutionServiceFactory {

    @Autowired
    private ArithmeticOperationsFactory arithmeticOperationsFactory;

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ExecutionService getExecutionService() {
        ExecutionService executionService = new ExecutionService();
        executionService.setExecutionContext(new ExecutionContext());
        executionService.registerInstructionHandler(PUSH, getPushInstructionHandler());
        executionService.registerInstructionHandler(ADD, addInstructionHandler());
        return executionService;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public AddInstructionHandler addInstructionHandler() {
        AddInstructionHandler addInstructionHandler = new AddInstructionHandler();
        addInstructionHandler.setArithmeticOperationsFactory(arithmeticOperationsFactory);
        return addInstructionHandler;
    }


    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PushInstructionHandler getPushInstructionHandler() {
        return new PushInstructionHandler();
    }
}
