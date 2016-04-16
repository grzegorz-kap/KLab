package com.klab.interpreter.service.functions.matrix.generation;

import com.klab.interpreter.service.functions.InternalFunction;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class EyeFunction extends AbstractMatrixGeneratorFunction {
    public EyeFunction() {
        super(1, 2, InternalFunction.EYE_FUNCTION);
    }

    @Override
    public ObjectData call(ObjectData[] data, int output) {
        return createMatrix(data, matrixFactory::eye);
    }
}
