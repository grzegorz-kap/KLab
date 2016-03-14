package com.klab.interpreter.service.functions.math;

import com.klab.interpreter.service.functions.InternalFunction;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class SqrtInternalFunction extends AbstractMathFunction {

    public SqrtInternalFunction() {
        super(1, 1, InternalFunction.SQRT_FUNCTION);
    }

    @Override
    public ObjectData process(NumericObject[] datas) {
        return functionsHolder.get(datas[0].getNumericType(), this).sqrt(datas[0]);
    }

}
