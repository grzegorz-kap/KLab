package interpreter.parsing.factory.operator;

import interpreter.parsing.model.tokens.operators.OperatorToken;

public interface OperatorProducer {
    OperatorToken getOperator();
}
