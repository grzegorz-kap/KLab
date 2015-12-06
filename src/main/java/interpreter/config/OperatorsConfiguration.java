package interpreter.config;

import org.ojalgo.matrix.task.SolverTask;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import interpreter.core.arithmetic.matrix.ojalgo.MatrixAdder;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixArrayConjuctionOperator;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixArrayDisjuctionOperator;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixArrayMult;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixArrayNegate;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixArrayPower;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixDivider;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixMultiplicator;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixPower;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixSubtractor;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixTranspose;
import interpreter.core.arithmetic.matrix.ojalgo.OjalgoMatrixOperator;
import interpreter.core.arithmetic.matrix.ojalgo.comparator.CompexOjalgoMatrixComparator;
import interpreter.core.arithmetic.matrix.ojalgo.comparator.DoubleOjalgoMatrixComparator;
import interpreter.core.arithmetic.scalar.ComplexComparator;
import interpreter.core.arithmetic.scalar.DoubleComparator;
import interpreter.core.arithmetic.scalar.ScalarNegator;
import interpreter.core.arithmetic.scalar.ScalarNumericObjectsOperator;
import interpreter.core.arithmetic.scalar.ScalarOperator;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;

@Configuration
public class OperatorsConfiguration {
	@Bean
	public OjalgoMatrixOperator<Double> getOjalgoMatrixOperatorDouble() {
		OjalgoMatrixOperator<Double> op = new OjalgoMatrixOperator<>();
		op.setAdder(new MatrixAdder<>(OjalgoDoubleMatrix::new));
		op.setComparator(new DoubleOjalgoMatrixComparator(OjalgoDoubleMatrix::new));
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
		op.setMatrixSubtractor(new MatrixSubtractor<>(OjalgoComplexMatrix::new));
		op.setMultiplicator(new MatrixMultiplicator<>(OjalgoComplexMatrix::new));
		op.setArrayMult(new MatrixArrayMult<>(OjalgoComplexMatrix::new));
		op.setMatrixPower(new MatrixPower<>(OjalgoComplexMatrix::new));
		op.setMatrixArrayPower(new MatrixArrayPower<>(OjalgoComplexMatrix::new));
		op.setMatrixTranspose(new MatrixTranspose<>(OjalgoComplexMatrix::new));
		op.setArrayOr(new MatrixArrayDisjuctionOperator<>(OjalgoComplexMatrix::new, ComplexNumber::new));
		op.setArrayAnd(new MatrixArrayConjuctionOperator<>(OjalgoComplexMatrix::new, ComplexNumber::new));
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
