package interpreter.core.arithmetic.factory;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interpreter.core.arithmetic.NumericObjectsOperator;
import interpreter.types.NumericType;

@Service
public class StandardNumericObjectsNumericObjectsOperatorFactory implements NumericObjectsOperatorFactory {
	private NumericObjectsOperator[] operators;
	
    @Override
    public NumericObjectsOperator getOperator(NumericType type) {
        return operators[type.getIndex()];
    }
    
    @Autowired
    public void setOperators(Set<NumericObjectsOperator> operators) {
		this.operators = new NumericObjectsOperator[NumericType.values().length];
		operators.forEach(op -> this.operators[op.getSupportedType().getIndex()] = op);
	}
}
