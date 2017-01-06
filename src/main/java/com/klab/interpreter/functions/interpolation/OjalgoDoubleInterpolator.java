package com.klab.interpreter.functions.interpolation;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.MatrixFactory;
import com.klab.interpreter.types.matrix.MatrixFactoryHolder;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.function.polynomial.PrimitivePolynomial;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OjalgoDoubleInterpolator implements LagrangeInterpolator<Double>, InitializingBean {
    private MatrixFactoryHolder matrixFactoryHolder;
    private MatrixFactory<Double> doubleMatrixFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        doubleMatrixFactory = matrixFactoryHolder.get(NumericType.MATRIX_DOUBLE);
    }

    @Override
    public Matrix<Double> interpolate(Matrix<Double> x, Matrix<Double> y, int degree) {
        PrimitivePolynomial polynomial = new PrimitivePolynomial(degree);
        polynomial.estimate(((OjalgoAbstractMatrix) x).getLazyStore(), ((OjalgoAbstractMatrix) y).getLazyStore());
        Matrix<Double> coefficients = doubleMatrixFactory.create(1, polynomial.size());
        for (int i = polynomial.size() - 1; i >= 0; i--) {
            coefficients.set(0, i, polynomial.get(i));
        }
        return coefficients;
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