package com.klab.interpreter.parsing.utils;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorCode;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken;

public class ExpressionUtils {
    private ExpressionUtils() {
    }

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

    public static boolean isAssignmentTargetRecursive(Expression<ParseToken> expression) {
        Expression<ParseToken> parent = expression.getParent();
        while (parent != null && !(parent.getValue().getParseClass().equals(ParseClass.OPERATOR) && parent.getValue().getToken().getLexeme().equals("="))) {
            parent = parent.getParent();
        }
        return parent != null && parent.getValue().getParseClass().equals(ParseClass.OPERATOR) && parent.getValue().getToken().getLexeme().equals("=");
    }
}
