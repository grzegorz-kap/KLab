package interpreter.types.converters;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.core.math.utils.SizeUtils;
import interpreter.types.NumericObject;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.Scalar;

import static interpreter.commons.exception.InterpreterCastException.CANNOT_CAST_MATRIX_TO_SCALAR;

public abstract class AbstractConverter<N extends NumericObject> implements Converter {

    protected abstract N convert(Scalar scalar);

    public abstract N convert(Matrix<? extends Number> matrix);

    @Override
    public NumericObject convert(NumericObject numericObject) {
        if (supportTo().equals(numericObject.getNumericType())) {
            return numericObject;
        }
        return numericObject instanceof Scalar ? convert(((Scalar) numericObject)) : convert(((Matrix) numericObject));
    }

    protected Number evaluteToScalar(Matrix<? extends Number> matrix) {
        if (SizeUtils.isScalar(matrix)) {
            return matrix.get(0);
        }
        throw new InterpreterCastException(CANNOT_CAST_MATRIX_TO_SCALAR);
    }
}
