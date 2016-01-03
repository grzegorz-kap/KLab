package interpreter.parsing.utils;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.parsing.model.tokens.operators.OperatorToken;

public class ExpressionUtils {
    private ExpressionUtils() {}

    public static boolean isAssignmentTarget(Expression<ParseToken> expression) {
        Expression<ParseToken> parent = expression.getParent();
        if (parent == null) {
            return false;
        }
        ParseToken parentToken = parent.getValue();
        if (!(parentToken instanceof OperatorToken)) {
            return false;
        }
        OperatorToken operatorToken = (OperatorToken) parentToken;
        return operatorToken.getOperatorCode().equals(OperatorCode.ASSIGN) && parent.getChildren().get(0) == expression;
    }
}
