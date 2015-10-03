package interpreter.math.scalar;

import interpreter.commons.ObjectData;
import interpreter.parsing.model.NumberType;

public class NumberObject implements ObjectData {

    private NumberType numberType;

    public NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberType numberType) {
        this.numberType = numberType;
    }
}
