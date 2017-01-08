package com.klab.interpreter.functions.math;

import com.klab.interpreter.functions.InternalFunction;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class SqrtInternalFunction extends AbstractMathFunction {
    public SqrtInternalFunction() {
        super(1, 1, InternalFunction.SQRT_FUNCTION);
    }

    @Override
    public ObjectData process(NumericObject[] data) {
        return functionsHolder.get(data[0].getNumericType(), this).sqrt(data[0]);
    }
}
