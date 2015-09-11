package interpreter.parsing.factory;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.service.Parser;
import interpreter.parsing.service.ParserService;
import interpreter.parsing.service.handlers.NumberHandler;
import interpreter.parsing.service.handlers.OperatorHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ParserFactory {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Parser getParserService() {
        Parser parser = new ParserService();
        parser.addParseHandler(TokenClass.NUMBER, new NumberHandler());
        parser.addParseHandler(TokenClass.OPERATOR, new OperatorHandler());
        return parser;
    }
}
