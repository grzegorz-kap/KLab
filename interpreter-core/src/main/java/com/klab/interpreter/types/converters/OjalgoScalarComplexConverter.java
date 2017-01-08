package com.klab.interpreter.types.converters;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.scalar.ComplexScalar;
import com.klab.interpreter.types.scalar.Scalar;
import org.springframework.stereotype.Component;

@Component
public class OjalgoScalarComplexConverter extends AbstractConverter<ComplexScalar> {
    public OjalgoScalarComplexConverter() {
        super(ComplexScalar.class);
    }

    @Override
    protected ComplexScalar convert(Scalar<?> scalar) {
        return convert(scalar.getValue());
    }

    @Override
    public ComplexScalar convert(Matrix<? extends Number> matrix) {
        return new ComplexScalar(evaluateToScalar(matrix));
    }

    @Override
    public ComplexScalar convert(Number number) {
        return new ComplexScalar(number);
    }

    @Override
    public NumericType supportFrom() {
        return null;
    }

    @Override
    public NumericType supportTo() {
        return NumericType.COMPLEX_DOUBLE;
    }
}
