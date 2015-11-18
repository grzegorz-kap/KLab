package interpreter.parsing.model.tokens;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.types.NumericType;

public class NumberToken extends ParseToken {

    private NumericType numericType;

    public NumberToken() {
        setParseClass(ParseClass.NUMBER);
    }

    public NumericType getNumericType() {
        return numericType;
    }

    public void setNumericType(NumericType numericType) {
        this.numericType = numericType;
    }
}
