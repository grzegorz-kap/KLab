package interpreter.math;

import org.springframework.stereotype.Service;

@Service("numberScalarFactory")
public class StandardNumberScalarFactory implements NumberScalarFactory {


    @Override
    public NumberObject getDouble(Double value) {
        return new DoubleScalar(value);
    }
}
