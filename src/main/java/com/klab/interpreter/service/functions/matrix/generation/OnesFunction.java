package com.klab.interpreter.service.functions.matrix.generation;

import com.klab.interpreter.service.functions.InternalFunction;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class OnesFunction extends AbstractMatrixGeneratorFunction {

    public OnesFunction() {
        super(1, 2, InternalFunction.ONES_FUNCTION);
    }

    @Override
    public ObjectData call(ObjectData[] data, int output) {
        return createMatrix(data, matrixFactory::ones);
    }
}
