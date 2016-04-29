package com.klab.interpreter.service.functions.math;

import com.klab.interpreter.commons.exception.IllegalArgumentException;
import com.klab.interpreter.service.functions.AbstractInternalFunction;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public abstract class AbstractMathFunction extends AbstractInternalFunction {
    @Autowired
    protected MathFunctionsHolder functionsHolder;

    public AbstractMathFunction(int argsMin, int argsMax, String name) {
        super(argsMin, argsMax, name);
    }

    protected abstract ObjectData process(NumericObject[] datas);

    @Override
    public ObjectData call(ObjectData[] data, int output) {
        return process(Stream.of(data).map(this::mapToNumeric).toArray(NumericObject[]::new));
    }

    protected NumericObject mapToNumeric(ObjectData data) {
        if (!(data instanceof NumericObject)) {
            throw new IllegalArgumentException("Numeric object expected");
        }
        return (NumericObject) data;
    }
}
