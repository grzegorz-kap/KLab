package interpreter.types.scalar;

import interpreter.types.NumericObject;
import org.springframework.stereotype.Service;

@Service("numberScalarFactory")
public class StandardNumberScalarFactory implements NumberScalarFactory {

    @Override
    public NumericObject getDouble(String value) {
        if (value.endsWith("i")) {
            value = value.replace("i", "");
            return new ComplexScalar(0D, Double.valueOf(value));
        }
        return new DoubleScalar(Double.valueOf(value));
    }
}
