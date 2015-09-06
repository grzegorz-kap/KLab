package console;

import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.RegexTokenizer;
import interpreter.lexer.service.Tokenizer;
import interpreter.parsing.factory.ParserFactory;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.service.Parser;
import org.apache.commons.lang3.StringUtils;

public class App {
    private static int ident = 0;

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
        System.out.println(expressionPrint(expression));
    }

    public static String expressionPrint(Expression<ParseToken> expression) {
        StringBuilder builder = new StringBuilder("");

        for (int i = 0; i < ident * 2; i++) {
            builder.append(i % 2 == 0 ? '|' : ' ');
        }

        ParseToken token = expression.getValue();
        if (token != null) {
            builder.append(String.format("%s", token.getToken().getLexeme()));
        } else {
            builder.append("null");
        }

        builder.append('\n');

        if (expression instanceof ExpressionNode) {
            ident++;
            expression.getChildren().forEach(child -> builder.append(expressionPrint(child)));
            ident--;
        }
        return builder.toString();
    }
}
