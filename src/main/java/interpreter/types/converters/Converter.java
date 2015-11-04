package interpreter.types.converters;

import interpreter.types.NumericObject;
import interpreter.types.NumericType;

public interface Converter {
    NumericObject convert(NumericObject numericObject);

    NumericType supportFrom();

    NumericType supportTo();
}
