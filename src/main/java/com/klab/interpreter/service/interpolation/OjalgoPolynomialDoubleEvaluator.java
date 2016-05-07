package com.klab.interpreter.service.interpolation;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
import org.ojalgo.function.UnaryFunction;
import org.ojalgo.function.polynomial.PrimitivePolynomial;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.springframework.stereotype.Component;

@Component
public class OjalgoPolynomialDoubleEvaluator implements PolynomialEvaluator<Double> {
    @Override
    public Matrix<Double> evaluate(Matrix<Double> p, Matrix<Double> x) {
        PrimitivePolynomial polynomial = new PrimitivePolynomial((int) (p.getColumns() - 1));
        for (int i = (int) (p.getColumns() - 1); i >= 0; i--) {
            polynomial.set(i, p.get(0, i));
        }

        PrimitiveDenseStore result = PrimitiveDenseStore.FACTORY.makeZero(x.getRows(), x.getColumns());
        result.fillMatching(new UnaryFunction<Double>() {
            @Override
            public double invoke(double arg) {
                return polynomial.invoke(arg);
            }

            @Override
            public Double invoke(Double arg) {
                return polynomial.invoke(arg);
            }
        }, ((OjalgoAbstractMatrix) x).getLazyStore());

        return new OjalgoDoubleMatrix(result);
    }

    @Override
    public NumericType supports() {
        return NumericType.MATRIX_DOUBLE;
    }
}
