package interpreter.execution.factory;

import interpreter.execution.model.ExecutionContext;
import interpreter.execution.service.ExecutionService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ExecutionServiceFactory {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ExecutionService getExecutionService() {
        ExecutionService executionService = new ExecutionService();
        executionService.setExecutionContext(new ExecutionContext());
        return executionService;
    }
}
