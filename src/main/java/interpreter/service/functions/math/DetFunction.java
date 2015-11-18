package interpreter.service.functions.math;

import interpreter.service.functions.InternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class DetFunction extends AbstractMathFunction {

    public DetFunction() {
        super(1, 2, InternalFunction.DETERMINANT);
    }

    @Override
    protected ObjectData process(NumericObject[] datas) {
        return functionsHolder.get(datas[0].getNumericType(), this).det(datas[0]);
    }

}
