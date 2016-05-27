package com.klab.interpreter.parsing.utils;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.factory.operator.OperatorFactory;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorCode;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken;
import com.klab.interpreter.parsing.service.ParseContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpressionUtils {
    @Autowired
    private OperatorFactory operatorFactory;

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

    public boolean isUnaryOperator(ParseContextManager parseContextManager, int index) {
        String lexeme = parseContextManager.tokenAt(index).getLexeme();
        if (!lexeme.matches("[-+]")) {
            return false;
        }

        Token prev = parseContextManager.tokenAt(index - 1);
        Token next = parseContextManager.tokenAt(index + 1);
        TokenClass previousClass = prev == null ? null : prev.getTokenClass();
        TokenClass nextClass = next == null ? null : next.getTokenClass();
        if (previousClass == null || previousClass.isUnaryOpPrecursor()) {
            return true;
        }

        if (parseContextManager.getBalanceContext().isBalanceType(BalanceType.INSIDE_MATRIX)) {
            if (previousClass == TokenClass.SPACE && nextClass != TokenClass.SPACE || previousClass == TokenClass.OPEN_BRACKET) {
                return true;
            }
        }
        if (TokenClass.OPERATOR == previousClass) {
            OperatorToken previous = operatorFactory.getOperator(prev);
            return previous.getArgumentsNumber() > 1 || previous.getAssociativity() == OperatorAssociativity.RIGHT_TO_LEFT;
        }

        return false;
    }
}
