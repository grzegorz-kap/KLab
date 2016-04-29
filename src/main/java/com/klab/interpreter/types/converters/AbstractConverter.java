package com.klab.interpreter.types.converters;

import com.klab.interpreter.commons.exception.InterpreterCastException;
import com.klab.interpreter.core.math.utils.SizeUtils;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.scalar.Scalar;

import static com.klab.interpreter.commons.exception.InterpreterCastException.CANNOT_CAST_MATRIX_TO_SCALAR;

public abstract class AbstractConverter<N extends NumericObject> implements Converter<N> {
    private Class<N> clazz;

    public AbstractConverter(Class<N> clazz) {
        this.clazz = clazz;
    }

    protected abstract N convert(Scalar<?> scalar);

    public abstract N convert(Matrix<? extends Number> matrix);

    @Override
    public N convert(NumericObject numericObject) {
        if (supportTo().equals(numericObject.getNumericType())) {
            return clazz.cast(numericObject);
        }
        if (numericObject instanceof Scalar) {
            return convert(((Scalar<?>) numericObject));
        } else {
            return convert(((Matrix<?>) numericObject));
        }
    }

    protected Number evaluateToScalar(Matrix<? extends Number> matrix) {
        if (SizeUtils.isScalar(matrix)) {
            return matrix.getNumber(0);
        }
        throw new InterpreterCastException(CANNOT_CAST_MATRIX_TO_SCALAR);
    }
}
