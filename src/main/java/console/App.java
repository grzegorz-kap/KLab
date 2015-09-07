package console;

import config.ApplicationConfiguration;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.RegexTokenizer;
import interpreter.lexer.service.Tokenizer;
import interpreter.parsing.factory.ParserFactory;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.service.Parser;
import interpreter.parsing.utils.ExpressionPrinter;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.service.InstructionTranslator;
import interpreter.translate.service.InstructionTranslatorService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationConfiguration.class);
        ctx.refresh();

        Tokenizer tokenizer = new RegexTokenizer();
        TokenList tokens = tokenizer.readTokens("3*2-1/3+2");
        tokens.stream().forEach(
                token -> System.out.println(
                        String.format("%s\t %s \t\t %d %d",
                                token.getLexeme(),
                                token.getTokenClass(),
                                token.getLine(),
                                token.getColumn()
                        )
                )
        );

        Parser parser = ParserFactory.getParser();
        Expression<ParseToken> expression = parser.process(tokens);
        System.out.println(ExpressionPrinter.expressionToString(expression));

        InstructionTranslator instructionTranslator = ctx.getBean(InstructionTranslatorService.class);
        MacroInstruction macroInstruction = instructionTranslator.translate(expression);

    }
}
