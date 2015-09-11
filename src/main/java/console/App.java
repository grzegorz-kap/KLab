package console;

import config.ApplicationConfiguration;
import interpreter.core.InterpreterService;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.RegexTokenizer;
import interpreter.lexer.service.Tokenizer;
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

        InterpreterService interpreterService = ctx.getBean(InterpreterService.class);

        Tokenizer tokenizer = new RegexTokenizer();
        TokenList tokens = tokenizer.readTokens("3*2-12/3+2");
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

        System.out.println();

        Parser parser = ctx.getBean(Parser.class);
        Expression<ParseToken> expression = parser.process(tokens);
        System.out.println(ExpressionPrinter.expressionToString(expression));

        InstructionTranslator instructionTranslator = ctx.getBean(InstructionTranslatorService.class);
        MacroInstruction macroInstruction = instructionTranslator.translate(expression);

        interpreterService.start("3*2-12/3+2").forEach(instruction -> {
            System.out.print(instruction.getInstructionCode() + "\t");
            instruction.forEachObjectData(objectData -> System.out.print(objectData + "\t"));
            System.out.println();
        });
    }
}
