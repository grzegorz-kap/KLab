package com.klab.interpreter.types.converters;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.ObjectData;

public interface ConvertersHolder {
    Converter<?> getConverter(NumericType sourceType, NumericType destType);

    <T> T convert(ObjectData objectData, NumericType dest);

    NumericType scalarToMatrix(NumericType scalarType);

    NumericType promote(NumericType a, NumericType b);
}
