package interpreter.math;

import interpreter.commons.ObjectData;
import interpreter.parsing.model.NumberType;

public class NumberScalar extends ObjectData {

    private NumberType numberType;

    public NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberType numberType) {
        this.numberType = numberType;
    }
}
