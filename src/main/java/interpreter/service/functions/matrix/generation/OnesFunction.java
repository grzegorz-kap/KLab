package interpreter.service.functions.matrix.generation;

import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class OnesFunction extends AbstractMatrixGeneratorFunction {

    public OnesFunction() {
        super(1, 2, InternalFunction.ONES_FUNCTION);
    }

    @Override
    public ObjectData call(ObjectData[] datas) {
        return createMatrix(datas, matrixFactory::createOnesDouble);
    }

}
