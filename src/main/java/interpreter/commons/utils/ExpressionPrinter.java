package interpreter.commons.utils;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionNode;
import org.springframework.stereotype.Component;

@Component
public class ExpressionPrinter {

    public String expressionToString(Expression<ParseToken> expression) {
        return expressionToString(expression, 0);
    }

    private String expressionToString(Expression<ParseToken> expression, int ident) {
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
            expression.getChildren().forEach(
                    child -> builder.append(expressionToString(child, ident + 1))
            );
        }
        return builder.toString();
    }
}
