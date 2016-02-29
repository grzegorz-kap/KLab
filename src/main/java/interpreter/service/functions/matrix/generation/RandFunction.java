package interpreter.service.functions.matrix.generation;

import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;
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
