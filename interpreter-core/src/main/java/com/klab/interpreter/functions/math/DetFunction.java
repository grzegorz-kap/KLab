package com.klab.interpreter.functions.math;

import com.klab.interpreter.functions.InternalFunction;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class DetFunction extends AbstractMathFunction {

    public DetFunction() {
        super(1, 2, InternalFunction.DETERMINANT);
    }

    @Override
    protected ObjectData process(NumericObject[] data) {
        return functionsHolder.get(data[0].getNumericType(), this).det(data[0]);
    }

}
