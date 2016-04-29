package com.klab.interpreter.types.converters;

import com.klab.interpreter.types.NumericType;

public interface ConvertersHolder {
    Converter<?> getConverter(NumericType sourceType, NumericType destType);

    NumericType scalarToMatrix(NumericType scalarType);

    NumericType promote(NumericType a, NumericType b);
}
