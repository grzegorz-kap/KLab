package interpreter.service.functions.matrix.generation;

import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class EyeFunction extends AbstractMatrixGeneratorFunction {

    public EyeFunction() {
        super(1, 2, InternalFunction.EYE_FUNCTION);
    }

    @Override
    public ObjectData call(ObjectData[] datas) {
        return createMatrix(datas, matrixFactory::createEyeDouble);
    }

}
