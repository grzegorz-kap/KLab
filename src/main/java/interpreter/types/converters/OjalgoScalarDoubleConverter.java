package interpreter.types.converters;

import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.DoubleScalar;
import interpreter.types.scalar.Scalar;
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
