package com.klab.interpreter.functions.math;

import com.klab.interpreter.functions.InternalFunction;
import com.klab.interpreter.functions.exception.InternalFunctionCallException;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class InvInternalFunction extends AbstractMathFunction {

    public InvInternalFunction() {
        super(1, 1, InternalFunction.INV_FUNCTION);
    }

    @Override
    public ObjectData process(NumericObject[] data) {
        try {
            return functionsHolder.get(data[0].getNumericType(), this).inv(data[0]);
        } catch (Exception e) {
            throw new InternalFunctionCallException(this, e);
        }
    }
}
