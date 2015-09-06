package console;

import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.RegexTokenizer;
import interpreter.lexer.service.Tokenizer;
import interpreter.parsing.factory.ParserFactory;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.service.Parser;
import interpreter.parsing.utils.ExpressionPrinter;

public class App {

    public static void main(String[] args) {
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
    }
}
