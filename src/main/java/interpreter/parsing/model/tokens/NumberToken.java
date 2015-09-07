package interpreter.parsing.model.tokens;

import interpreter.parsing.model.NumberType;
import interpreter.parsing.model.ParseToken;

public class NumberToken<T extends Number> extends ParseToken {

    private T valueRe;
    private T valueIm;
    private NumberType numberType;

    public T getValueIm() {
        return valueIm;
    }

    public void setValueIm(T valueIm) {
        this.valueIm = valueIm;
    }

    public T getValueRe() {
        return valueRe;
    }

    public void setValueRe(T valueRe) {
        this.valueRe = valueRe;
    }

    public NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberType numberType) {
        this.numberType = numberType;
    }
}
