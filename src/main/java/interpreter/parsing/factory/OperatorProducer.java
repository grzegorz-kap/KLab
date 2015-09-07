package interpreter.parsing.factory;

import interpreter.parsing.model.tokens.operators.OperatorToken;

public interface OperatorProducer {
    OperatorToken get();
}
