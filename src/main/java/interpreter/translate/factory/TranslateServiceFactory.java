package interpreter.translate.factory;

import interpreter.lexer.model.TokenClass;
import interpreter.math.NumberScalarFactory;
import interpreter.translate.handlers.NumberTranslateHandler;
import interpreter.translate.service.InstructionTranslator;
import interpreter.translate.service.InstructionTranslatorService;
import interpreter.translate.service.TranslateContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TranslateServiceFactory {

    @Autowired
    private NumberScalarFactory numberScalarFactory;

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public NumberTranslateHandler getNumberTranslateHandler() {
        NumberTranslateHandler numberTranslateHandler = new NumberTranslateHandler();
        numberTranslateHandler.setNumberScalarFactory(numberScalarFactory);
        return numberTranslateHandler;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public InstructionTranslator getInstructionTranslator() {
        InstructionTranslatorService instructionTranslatorService = new InstructionTranslatorService();
        instructionTranslatorService.setTranslateContextManager(new TranslateContextManager());
        instructionTranslatorService.addTranslateHandler(TokenClass.NUMBER, getNumberTranslateHandler());
        return instructionTranslatorService;
    }
}
