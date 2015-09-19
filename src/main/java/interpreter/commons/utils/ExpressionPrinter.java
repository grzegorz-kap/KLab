package interpreter.commons.utils;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionNode;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.IntStream;

@Component
public class ExpressionPrinter {

    public String expressionToString(Expression<ParseToken> expression) {
        return expressionToString(expression, 0);
    }

    private String expressionToString(Expression<ParseToken> expression, int ident) {
        StringBuilder builder = new StringBuilder("");
        IntStream.range(0, ident * 2).forEach(i -> builder.append(i % 2 == 0 ? '|' : ' '));

        ParseToken token = expression.getValue();
        if (Objects.nonNull(token)) {
            builder.append(String.format("%s", token.getToken().getLexeme()));
        } else {
            builder.append("null");
        }

        builder.append('\n');

        if (expression instanceof ExpressionNode) {
            expression.getChildren().forEach(
                    child -> builder.append(expressionToString(child, ident + 1))
            );
        }
        return builder.toString();
    }
}
