package interpreter.types.converters;

import interpreter.types.NumericObject;
import interpreter.types.NumericType;

public interface Converter<N extends NumericObject> {
    N convert(NumericObject numericObject);

    N convert(Number number);

    NumericType supportFrom();

    NumericType supportTo();
}
