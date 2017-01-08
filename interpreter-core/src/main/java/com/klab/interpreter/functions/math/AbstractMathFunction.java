package com.klab.interpreter.functions.math;

import com.klab.interpreter.commons.exception.IllegalArgumentException;
import com.klab.interpreter.functions.AbstractInternalFunction;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.converters.ConvertersHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;
import java.util.stream.Stream;

public abstract class AbstractMathFunction extends AbstractInternalFunction {
    MathFunctionsHolder functionsHolder;

    private ConvertersHolder convertersHolder;

    public AbstractMathFunction(int argsMin, int argsMax, String name) {
        super(argsMin, argsMax, name);
    }

    protected abstract ObjectData process(NumericObject[] data);

    protected NumericObject convert(NumericObject a, NumericType type) {
        return convertersHolder.convert(a, type);
    }

    @Override
    public ObjectData call(ObjectData[] args, int output) {
        NumericObject[] converted = Stream.of(args)
                .map(data -> {
                    if (!(data instanceof NumericObject)) {
                        throw new IllegalArgumentException("Numeric object expected");
                    }
                    return (NumericObject) data;
                })
                .toArray(NumericObject[]::new);
        return process(converted);
    }

    ObjectData single(NumericObject[] data, Function<MathFunctions, Function<NumericObject, NumericObject>> producer) {
        MathFunctions mathFunctions = functionsHolder.get(data[0].getNumericType(), this);
        return producer.apply(mathFunctions).apply(data[0]);
    }

    NumericType promote(NumericObject a, NumericObject b) {
        return convertersHolder.promote(a.getNumericType(), b.getNumericType());
    }

    @Autowired
    public void setFunctionsHolder(MathFunctionsHolder functionsHolder) {
        this.functionsHolder = functionsHolder;
    }

    @Autowired
    public void setConvertersHolder(ConvertersHolder convertersHolder) {
        this.convertersHolder = convertersHolder;
    }
}
