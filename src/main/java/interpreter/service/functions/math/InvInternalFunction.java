package interpreter.service.functions.math;

import interpreter.service.functions.InternalFunction;
import interpreter.service.functions.exception.InternalFunctionCallException;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class InvInternalFunction extends AbstractMathFunction {

    public InvInternalFunction() {
        super(1, 1, InternalFunction.INV_FUNCTION);
    }

    @Override
    public ObjectData process(NumericObject[] data) {
        try {
            return functionsHolder.get(data[0].getNumericType(), this).inv(data[0]);
        } catch (Exception e) {
            throw new InternalFunctionCallException(this, e);
        }
    }
}
