package com.klab.interpreter.functions.matrix.generation;

import com.klab.interpreter.functions.InternalFunction;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class ZerosFunction extends AbstractMatrixGeneratorFunction {

    public ZerosFunction() {
        super(1, 2, InternalFunction.ZEROS_FUNCTION);
    }

    @Override
    public ObjectData call(ObjectData[] data, int output) {
        return createMatrix(data, matrixFactory::zeros);
    }

}
