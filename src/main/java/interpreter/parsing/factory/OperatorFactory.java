package interpreter.parsing.factory;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.tokens.operators.OperatorToken;

import java.util.HashMap;
import java.util.Map;

import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT;
import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.RIGHT_TO_LEFT;
import static interpreter.parsing.model.tokens.operators.OperatorCode.*;
import static interpreter.parsing.model.tokens.operators.OperatorPriority.*;

public abstract class OperatorFactory {

    private static Map<String, OperatorProducer> OPERATOR_PRODUCERS = new HashMap<>();

    static {
        OPERATOR_PRODUCERS.put("=", () -> new OperatorToken(2, RIGHT_TO_LEFT, LEVEL_10, ASSIGN));
        OPERATOR_PRODUCERS.put("+", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_20, ADD));
        OPERATOR_PRODUCERS.put("-", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_20, SUB));
        OPERATOR_PRODUCERS.put("*", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_30, MULT));
        OPERATOR_PRODUCERS.put("/", () -> new OperatorToken(2, LEFT_TO_RIGHT, LEVEL_30, DIV));
    }

    public static OperatorToken get(final Token token) {
        OperatorProducer operatorProducer = OPERATOR_PRODUCERS.get(token.getLexeme());
        OperatorToken operatorToken = operatorProducer.get();
        operatorToken.setToken(token);
        return operatorToken;
    }
}
