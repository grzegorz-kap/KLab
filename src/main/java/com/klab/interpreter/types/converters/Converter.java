package com.klab.interpreter.types.converters;

import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;

public interface Converter<N extends NumericObject> {
    N convert(NumericObject numericObject);

    N convert(Number number);

    NumericType supportFrom();

    NumericType supportTo();
}
