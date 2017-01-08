package com.klab.interpreter.types.converters;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.scalar.DoubleScalar;
import com.klab.interpreter.types.scalar.Scalar;
import org.springframework.stereotype.Component;

@Component
public class OjalgoScalarDoubleConverter extends AbstractConverter<DoubleScalar> {
    public OjalgoScalarDoubleConverter() {
        super(DoubleScalar.class);
    }

    @Override
    public DoubleScalar convert(Number number) {
        return new DoubleScalar(number.doubleValue());
    }

    @Override
    public NumericType supportFrom() {
        return null;
    }

    @Override
    public NumericType supportTo() {
        return NumericType.DOUBLE;
    }

    @Override
    protected DoubleScalar convert(Scalar<?> scalar) {
        return new DoubleScalar(scalar.getValue().doubleValue());
    }

    @Override
    public DoubleScalar convert(Matrix<? extends Number> matrix) {
        return new DoubleScalar(evaluateToScalar(matrix).doubleValue());
    }
}
