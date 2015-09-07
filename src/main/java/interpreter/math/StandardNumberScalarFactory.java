package interpreter.math;

import org.springframework.stereotype.Service;

@Service("numberScalarFactory")
public class StandardNumberScalarFactory implements NumberScalarFactory {


    @Override
    public NumberScalar getDouble(Double value) {
        return new DoubleScalar(value);
    }
}
