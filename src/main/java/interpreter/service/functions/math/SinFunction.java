package interpreter.service.functions.math;

import interpreter.service.functions.InternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class SinFunction extends AbstractMathFunction {

    public SinFunction() {
        super(1, 1, InternalFunction.SINUS);
    }

    @Override
    protected ObjectData process(NumericObject[] datas) {
        return functionsHolder.get(datas[0].getNumericType(), this).sin(datas[0]);
    }

}
