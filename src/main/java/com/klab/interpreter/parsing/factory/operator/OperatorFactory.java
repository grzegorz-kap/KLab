package com.klab.interpreter.parsing.factory.operator;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT;
import static com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity.RIGHT_TO_LEFT;
import static com.klab.interpreter.parsing.model.tokens.operators.OperatorCode.*;
import static com.klab.interpreter.parsing.model.tokens.operators.OperatorPriority.*;

@Service
public class OperatorFactory {
    private Map<String, Function<Token, OperatorToken>> operatorsProducer = new HashMap<>();

    public OperatorFactory() {
        operatorsProducer.put("=", (t) -> new OperatorToken(t, 2, RIGHT_TO_LEFT, LEVEL_10, ASSIGN));
        operatorsProducer.put("&&", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_14, AND));
        operatorsProducer.put("||", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_14, OR));
        operatorsProducer.put("|", (t) -> new OperatorToken(t, 2, RIGHT_TO_LEFT, LEVEL_15, AOR));
        operatorsProducer.put("&", (t) -> new OperatorToken(t, 2, RIGHT_TO_LEFT, LEVEL_15, AAND));
        operatorsProducer.put("==", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_20, EQ));
        operatorsProducer.put("~=", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_20, NEQ));
        operatorsProducer.put(">", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_20, GT));
        operatorsProducer.put(">=", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_20, GE));
        operatorsProducer.put("<", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_20, LT));
        operatorsProducer.put("<=", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_20, LE));
        operatorsProducer.put(":", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_25, RANGE));
        operatorsProducer.put("$::", (t) -> new OperatorToken(t, 3, LEFT_TO_RIGHT, LEVEL_25, RANGE3));
        operatorsProducer.put("+", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_30, ADD));
        operatorsProducer.put("-", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_30, SUB));
        operatorsProducer.put("*", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_40, MULT));
        operatorsProducer.put(".*", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_40, AMULT));
        operatorsProducer.put("/", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_40, DIV));
        operatorsProducer.put("./", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_40, ADIV));
        operatorsProducer.put("~", (t) -> new OperatorToken(t, 1, RIGHT_TO_LEFT, LEVEL_45, NOT));
        operatorsProducer.put("$-", (t) -> new OperatorToken(t, 1, RIGHT_TO_LEFT, LEVEL_45, NEG));
        operatorsProducer.put("$+", (t) -> new OperatorToken(t, 1, RIGHT_TO_LEFT, LEVEL_45, PLUS));
        operatorsProducer.put("^", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_50, POW));
        operatorsProducer.put(".^", (t) -> new OperatorToken(t, 2, LEFT_TO_RIGHT, LEVEL_50, APOW));
        operatorsProducer.put("'", (t) -> new OperatorToken(t, 1, LEFT_TO_RIGHT, LEVEL_50, TRANSPOSE));
    }

    public OperatorToken getOperator(final Token token) {
        return operatorsProducer.get(token.getLexeme()).apply(token);
    }

    public OperatorToken getOperator(String key, Token token) {
        return operatorsProducer.get(key).apply(token);
    }
}
