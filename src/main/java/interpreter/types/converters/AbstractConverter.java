package interpreter.types.converters;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.core.math.utils.SizeUtils;
import interpreter.types.NumericObject;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.Scalar;

import static interpreter.commons.exception.InterpreterCastException.CANNOT_CAST_MATRIX_TO_SCALAR;

public abstract class AbstractConverter<N extends NumericObject> implements Converter<N> {

    protected abstract N convert(Scalar scalar);

    public abstract N convert(Matrix<? extends Number> matrix);

    @Override
    public N convert(NumericObject numericObject) {
        if (supportTo().equals(numericObject.getNumericType())) {
            return (N) numericObject;
        }
        if (numericObject instanceof Scalar) {
            return convert(((Scalar) numericObject));
        } else {
            return convert(((Matrix<?>) numericObject));
        }
    }

    protected Number evaluateToScalar(Matrix<? extends Number> matrix) {
        if (SizeUtils.isScalar(matrix)) {
            return matrix.get(0);
        }
        throw new InterpreterCastException(CANNOT_CAST_MATRIX_TO_SCALAR);
    }
}
