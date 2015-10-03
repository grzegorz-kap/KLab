package interpreter.math;

import interpreter.commons.ObjectData;
import interpreter.parsing.model.NumericType;

public interface NumericObject extends ObjectData {

    NumericType getNumericType();

    void setNumericType(NumericType numericType);
}
