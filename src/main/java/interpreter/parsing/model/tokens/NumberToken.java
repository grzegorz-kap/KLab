package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.types.NumericType;

public class NumberToken extends ParseToken {
    private NumericType numericType;

    public NumberToken(Token token, NumericType numericType) {
        super(token, ParseClass.NUMBER);
        this.numericType = numericType;
    }

    public NumericType getNumericType() {
        return numericType;
    }
}
