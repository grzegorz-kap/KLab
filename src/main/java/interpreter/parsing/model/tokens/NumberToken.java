package interpreter.parsing.model.tokens;

import interpreter.parsing.model.NumericType;
import interpreter.parsing.model.ParseToken;

public class NumberToken extends ParseToken {

    private NumericType numericType;

    public NumericType getNumericType() {
        return numericType;
    }

    public void setNumericType(NumericType numericType) {
        this.numericType = numericType;
    }
}
