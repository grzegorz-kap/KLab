package interpreter.parsing.model.tokens;

import interpreter.parsing.model.NumberType;
import interpreter.parsing.model.ParseToken;

public class NumberToken extends ParseToken {

    private NumberType numberType;

    public NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberType numberType) {
        this.numberType = numberType;
    }
}
