package interpreter.execution.factory;

import interpreter.core.arithmetic.factory.ArithmeticOperationsFactory;
import interpreter.execution.handlers.PushInstructionHandler;
import interpreter.execution.handlers.arithmetic.AddInstructionHandler;
import interpreter.execution.handlers.arithmetic.DivInstructionHandler;
import interpreter.execution.handlers.arithmetic.MultInstructionHandler;
import interpreter.execution.handlers.arithmetic.SubInstructionHandler;
import interpreter.execution.model.ExecutionContext;
import interpreter.execution.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static interpreter.translate.model.instruction.InstructionCode.*;

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
        executionService.registerInstructionHandler(SUB, subInstructionHandler());
        executionService.registerInstructionHandler(MULT, multInstructionHandler());
        executionService.registerInstructionHandler(DIV, divInstructionHandler());
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
    public SubInstructionHandler subInstructionHandler() {
        SubInstructionHandler subInstructionHandler = new SubInstructionHandler();
        subInstructionHandler.setArithmeticOperationsFactory(arithmeticOperationsFactory);
        return subInstructionHandler;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public MultInstructionHandler multInstructionHandler() {
        MultInstructionHandler multInstructionHandler = new MultInstructionHandler();
        multInstructionHandler.setArithmeticOperationsFactory(arithmeticOperationsFactory);
        return multInstructionHandler;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DivInstructionHandler divInstructionHandler() {
        DivInstructionHandler divInstructionHandler = new DivInstructionHandler();
        divInstructionHandler.setArithmeticOperationsFactory(arithmeticOperationsFactory);
        return divInstructionHandler;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public PushInstructionHandler getPushInstructionHandler() {
        return new PushInstructionHandler();
    }
}
