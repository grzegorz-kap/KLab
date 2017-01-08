package com.klab.interpreter.types.converters;

import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;

public interface Converter<N extends NumericObject> {
    <T extends N> T convert(NumericObject numericObject);

    <T extends N> T convert(Number number);

    NumericType supportFrom();

    NumericType supportTo();
}
