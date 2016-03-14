package com.klab.interpreter.types.converters;

import com.klab.interpreter.types.NumericType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class ConvertersHolderImpl implements ConvertersHolder {
    private static final Map<NumericType, NumericType> SCALAR_TO_MATRIX_MAP = new EnumMap<NumericType, NumericType>(NumericType.class) {{
        put(NumericType.DOUBLE, NumericType.MATRIX_DOUBLE);
        put(NumericType.COMPLEX_DOUBLE, NumericType.COMPLEX_MATRIX);
    }};
    private Converter<?>[] converters = new Converter[NumericType.values().length];

    @Override
    public Converter<?> getConverter(NumericType sourceType, NumericType destType) {
        return converters[destType.getIndex()];
    }

    @Override
    public NumericType scalarToMatrix(NumericType scalarType) {
        return SCALAR_TO_MATRIX_MAP.get(scalarType);
    }

    @Override
    public NumericType promote(NumericType a, NumericType b) {
        if (!a.getNumericPriority().isScalar() || !b.getNumericPriority().isScalar()) {
            a = Optional.ofNullable(scalarToMatrix(a)).orElse(a);
            b = Optional.ofNullable(scalarToMatrix(b)).orElse(b);
        }
        return a.getPriority() > b.getPriority() ? a : b;
    }

    @Autowired
    public void setConverters(Set<Converter<?>> converters) {
        converters.forEach((converter) -> this.converters[converter.supportTo().getIndex()] = converter);
    }
}
