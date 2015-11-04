package interpreter.types.converters;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.core.math.utils.SizeUtils;
import interpreter.types.NumericObject;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.Scalar;

import static interpreter.commons.exception.InterpreterCastException.CANNOT_CAST_MATRIX_TO_SCALAR;

public abstract class AbstractConverter implements Converter {

    protected abstract NumericObject convert(Scalar scalar);

    protected abstract NumericObject convert(Matrix matrix);

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
