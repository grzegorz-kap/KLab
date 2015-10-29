package interpreter.service.functions.math;

import org.ojalgo.function.UnaryFunction;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.matrix.task.InverterTask;
import org.springframework.stereotype.Service;

import interpreter.parsing.model.NumericType;
import interpreter.types.NumericObject;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;

@Service
public class OjalgoDoubleMathFunction implements MathFunctions {

	private PhysicalStore.Factory<Double, PrimitiveDenseStore> factory = PrimitiveDenseStore.FACTORY;
	private UnaryFunction<Double> sqrtFunction = factory.function().sqrt();
	private UnaryFunction<Double> sinFunction = factory.function().sin();

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

}
