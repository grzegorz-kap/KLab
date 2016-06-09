package com.klab.interpreter.config;

import com.klab.interpreter.core.arithmetic.matrix.ojalgo.*;
import com.klab.interpreter.core.arithmetic.matrix.ojalgo.comparator.CompexOjalgoMatrixComparator;
import com.klab.interpreter.core.arithmetic.matrix.ojalgo.comparator.DoubleOjalgoMatrixComparator;
import com.klab.interpreter.core.arithmetic.scalar.*;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
import org.ojalgo.matrix.task.SolverTask;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperatorsConfiguration {
    @Bean
    public OjalgoMatrixOperator<Double> getOjalgoMatrixOperatorDouble() {
        OjalgoMatrixOperator<Double> op = new OjalgoMatrixOperator<>();
        op.setAdder(new MatrixAdder<>(OjalgoDoubleMatrix::new));
        op.setComparator(new DoubleOjalgoMatrixComparator(OjalgoDoubleMatrix::new));
        op.setArrayDivider(new MatrixArrayDivider<>(OjalgoDoubleMatrix::new));
        op.setDivider(new MatrixDivider<>(SolverTask.PRIMITIVE, OjalgoDoubleMatrix::new));
        op.setMatrixSubtractor(new MatrixSubtractor<>(OjalgoDoubleMatrix::new));
        op.setMultiplicator(new MatrixMultiplicator<>(OjalgoDoubleMatrix::new));
        op.setArrayMult(new MatrixArrayMult<>(OjalgoDoubleMatrix::new));
        op.setMatrixPower(new MatrixPower<>(OjalgoDoubleMatrix::new));
        op.setMatrixArrayPower(new MatrixArrayPower<>(OjalgoDoubleMatrix::new));
        op.setMatrixTranspose(new MatrixTranspose<>(OjalgoDoubleMatrix::new));
        op.setArrayAnd(new MatrixArrayConjuctionOperator<>(OjalgoDoubleMatrix::new, Double::new));
        op.setArrayOr(new MatrixArrayDisjuctionOperator<>(OjalgoDoubleMatrix::new, Double::new));
        op.setNegator(new MatrixArrayNegate<>(OjalgoDoubleMatrix::new, OjalgoDoubleMatrix.class));
        op.setSupportedType(NumericType.MATRIX_DOUBLE);
        return op;
    }

    @Bean
    public OjalgoMatrixOperator<ComplexNumber> getOjalgoMatrixOperatorComplex() {
        OjalgoMatrixOperator<ComplexNumber> op = new OjalgoMatrixOperator<>();
        op.setAdder(new MatrixAdder<>(OjalgoComplexMatrix::new));
        op.setComparator(new CompexOjalgoMatrixComparator(OjalgoComplexMatrix::new));
        op.setDivider(new MatrixDivider<>(SolverTask.COMPLEX, OjalgoComplexMatrix::new));
        op.setArrayDivider(new MatrixArrayDivider<>(OjalgoComplexMatrix::new));
        op.setMatrixSubtractor(new MatrixSubtractor<>(OjalgoComplexMatrix::new));
        op.setMultiplicator(new MatrixMultiplicator<>(OjalgoComplexMatrix::new));
        op.setArrayMult(new MatrixArrayMult<>(OjalgoComplexMatrix::new));
        op.setMatrixPower(new MatrixPower<>(OjalgoComplexMatrix::new));
        op.setMatrixArrayPower(new MatrixArrayPower<>(OjalgoComplexMatrix::new));
        op.setMatrixTranspose(new MatrixTranspose<>(OjalgoComplexMatrix::new));
        op.setArrayOr(new MatrixArrayDisjuctionOperator<>(OjalgoComplexMatrix::new, ComplexNumber::valueOf));
        op.setArrayAnd(new MatrixArrayConjuctionOperator<>(OjalgoComplexMatrix::new, ComplexNumber::valueOf));
        op.setNegator(new MatrixArrayNegate<>(OjalgoComplexMatrix::new, OjalgoComplexMatrix.class));
        op.setSupportedType(NumericType.COMPLEX_MATRIX);
        return op;
    }

    @Bean
    public ScalarNumericObjectsOperator<Double> scalarDoubleOperator() {
        ScalarNumericObjectsOperator<Double> op = new ScalarNumericObjectsOperator<>();
        op.setAdder(ScalarOperator.DOUBLE_ADD);
        op.setSub(ScalarOperator.DOUBLE_SUB);
        op.setDiv(ScalarOperator.DOUBLE_DIV);
        op.setMult(ScalarOperator.DOUBLE_MULT);
        op.setPow(ScalarOperator.DOUBLE_POW);
        op.setAnd(ScalarOperator.DOUBLE_AND);
        op.setOr(ScalarOperator.DOUBLE_OR);
        op.setNegator(ScalarNegator.DOUBLE_NEGATOR);
        op.setComparator(new DoubleComparator());
        op.setSupportedType(NumericType.DOUBLE);
        return op;
    }

    @Bean
    public ScalarNumericObjectsOperator<ComplexNumber> scalarComplexOperator() {
        ScalarNumericObjectsOperator<ComplexNumber> op = new ScalarNumericObjectsOperator<>();
        op.setAdder(ScalarOperator.COMPLEX_ADDER);
        op.setSub(ScalarOperator.COMPLEX_SUB);
        op.setDiv(ScalarOperator.COMPLEX_DIVIDER);
        op.setMult(ScalarOperator.COMPLEX_MULT);
        op.setAnd(ScalarOperator.COMPLEX_AND);
        op.setOr(ScalarOperator.COMPLEX_OR);
        op.setComparator(new ComplexComparator());
        op.setPow(ScalarOperator.COMPLEX_POW);
        op.setNegator(ScalarNegator.COMPLEX_NEGATOR);
        op.setSupportedType(NumericType.COMPLEX_DOUBLE);
        return op;
    }
}
