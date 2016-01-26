package interpreter.service.functions.math;

import interpreter.service.functions.InternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class CosinusFunction extends AbstractMathFunction {

    public CosinusFunction() {
        super(1, 1, InternalFunction.COSINUS);
    }

    @Override
    protected ObjectData process(NumericObject[] data) {
        return functionsHolder.get(data[0].getNumericType(), this).cos(data[0]);
    }

}
