package com.klab.interpreter.functions.matrix.generation;

import com.klab.interpreter.functions.InternalFunction;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class RandFunction extends AbstractMatrixGeneratorFunction {
    public RandFunction() {
        super(1, 2, InternalFunction.RAND_FUNCTION);
    }

    @Override
    public ObjectData call(ObjectData[] data, int output) {
        return createMatrix(data, matrixFactory::rand);
    }
}
