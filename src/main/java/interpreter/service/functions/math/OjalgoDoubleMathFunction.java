package interpreter.service.functions.math;

import org.ojalgo.function.UnaryFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.matrix.task.DeterminantTask;
import org.ojalgo.matrix.task.InverterTask;
import org.springframework.stereotype.Service;

import interpreter.parsing.model.NumericType;
import interpreter.types.NumericObject;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import interpreter.types.scalar.DoubleScalar;

@Service
public class OjalgoDoubleMathFunction implements MathFunctions {

	private PhysicalStore.Factory<Double, PrimitiveDenseStore> factory = PrimitiveDenseStore.FACTORY;
	private UnaryFunction<Double> sqrtFunction = factory.function().sqrt();
	private UnaryFunction<Double> sinFunction = factory.function().sin();
	private UnaryFunction<Double> tanFunction = factory.function().tan();

	@Override
	public NumericType supports() {
		return NumericType.MATRIX_DOUBLE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NumericObject sqrt(NumericObject value) {
		OjalgoMatrix<Double> matrix = (OjalgoMatrix<Double>) value;
		return new OjalgoMatrix<Double>(matrix.getLazyStore().operateOnAll(sqrtFunction));
	}

	@SuppressWarnings("unchecked")
	@Override
	public NumericObject inv(NumericObject value) throws Exception {
		OjalgoMatrix<Double> matrix = (OjalgoMatrix<Double>) value;
		return new OjalgoMatrix<Double>(
				InverterTask.PRIMITIVE.make(matrix.getLazyStore()).invert(matrix.getLazyStore()));

	}

	@Override
	public NumericObject sin(NumericObject value) {
		@SuppressWarnings("unchecked")
		OjalgoMatrix<Double> matrix = (OjalgoMatrix<Double>) value;
		return new OjalgoMatrix<Double>(matrix.getLazyStore().operateOnAll(sinFunction));
	}

	@SuppressWarnings("unchecked")
	@Override
	public NumericObject det(NumericObject value) {
		MatrixStore<Double> matrix = ((OjalgoMatrix<Double>) value).getLazyStore() ;
		return new DoubleScalar(DeterminantTask.PRIMITIVE.make(matrix).calculateDeterminant(matrix));
	}

	@SuppressWarnings("unchecked")
	@Override
	public NumericObject tan(NumericObject value) {
		MatrixStore<Double> matrix = ((OjalgoMatrix<Double>) value).getLazyStore() ;
		return new OjalgoMatrix<Double>(matrix.operateOnAll(tanFunction));
	}

}
