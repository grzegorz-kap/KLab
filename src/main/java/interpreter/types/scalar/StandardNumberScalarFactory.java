package interpreter.types.scalar;

import interpreter.types.NumericObject;
import org.springframework.stereotype.Service;

@Service("numberScalarFactory")
public class StandardNumberScalarFactory implements NumberScalarFactory {

    @Override
    public NumericObject getDouble(Double value) {
        return new DoubleScalar(value);
    }
}
