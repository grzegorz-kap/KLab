package com.klab.interpreter.service.interpolation;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.MatrixFactory;
import com.klab.interpreter.types.matrix.MatrixFactoryHolder;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.function.polynomial.PrimitivePolynomial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OjalgoDoubleInterpolator implements LagrangeInterpolator<Double> {
    private MatrixFactoryHolder matrixFactoryHolder;

    @Override
    public Matrix<Double> interpolate(Matrix<Double> x, Matrix<Double> y, int degree) {
        PrimitivePolynomial primitivePolynomial = new PrimitivePolynomial(degree);
        primitivePolynomial.estimate(((OjalgoAbstractMatrix) x).getLazyStore(), ((OjalgoAbstractMatrix) y).getLazyStore());

        double[] doubles = primitivePolynomial.toRawCopy1D();
        MatrixFactory<Double> matrixFactory = matrixFactoryHolder.get(NumericType.MATRIX_DOUBLE);
        Matrix<Double> doubleMatrix = matrixFactory.create(1, doubles.length);

        int index = doubles.length;
        for (double aDouble : doubles) {
            doubleMatrix.set(0, --index, aDouble);
        }

        return doubleMatrix;
    }

    @Override
    public NumericType supports() {
        return NumericType.MATRIX_DOUBLE;
    }

    @Autowired
    public void setMatrixFactoryHolder(MatrixFactoryHolder matrixFactoryHolder) {
        this.matrixFactoryHolder = matrixFactoryHolder;
    }
}