package interpreter.config;

import org.ojalgo.matrix.task.SolverTask;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import interpreter.core.arithmetic.matrix.ojalgo.MatrixAdder;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixDivider;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixMultiplicator;
import interpreter.core.arithmetic.matrix.ojalgo.MatrixSubtractor;
import interpreter.core.arithmetic.matrix.ojalgo.OjalgoMatrixOperator;
import interpreter.core.arithmetic.matrix.ojalgo.comparator.CompexOjalgoMatrixComparator;
import interpreter.core.arithmetic.matrix.ojalgo.comparator.DoubleOjalgoMatrixComparator;
import interpreter.core.arithmetic.scalar.ComplexComparator;
import interpreter.core.arithmetic.scalar.DoubleComparator;
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
		op.setComparator(new ComplexComparator());
		op.setSupportedType(NumericType.COMPLEX_DOUBLE);
		return op;
	}
}
