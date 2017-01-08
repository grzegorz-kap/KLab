package com.klab.interpreter.core.arithmetic.factory;

import com.klab.interpreter.core.arithmetic.NumericObjectsOperator;
import com.klab.interpreter.types.NumericType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
