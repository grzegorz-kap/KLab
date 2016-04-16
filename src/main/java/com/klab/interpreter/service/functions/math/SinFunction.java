package com.klab.interpreter.service.functions.math;

import com.klab.interpreter.service.functions.InternalFunction;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class SinFunction extends AbstractMathFunction {
    public SinFunction() {
        super(1, 1, InternalFunction.SINUS);
    }

    @Override
    protected ObjectData process(NumericObject[] datas) {
        return functionsHolder.get(datas[0].getNumericType(), this).sin(datas[0]);
    }
}
