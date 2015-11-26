package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.NumericObjectsOperator;
import interpreter.types.NumericType;
import org.springframework.stereotype.Service;

@Service
public class StandardNumericObjectsNumericObjectsOperatorFactory implements NumericObjectsOperatorFactory {

    @Override
    public NumericObjectsOperator getOperator(NumericType type) {
        return null;
    }
}
