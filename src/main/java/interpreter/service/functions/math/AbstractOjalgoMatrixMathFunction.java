package interpreter.service.functions.math;

import org.ojalgo.function.UnaryFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.task.DeterminantTask;
import org.ojalgo.matrix.task.InverterTask;
import org.springframework.beans.factory.annotation.Required;

import interpreter.parsing.model.NumericType;
import interpreter.types.NumericObject;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;

public abstract class AbstractOjalgoMatrixMathFunction <T extends Number> implements MathFunctions {

	private UnaryFunction<T> sqrtFunction;
	private UnaryFunction<T> sinFunction;
	private UnaryFunction<T> tanFunction;
	private UnaryFunction<T> cosFunction;
	private InverterTask.Factory<T> invTask;
	private DeterminantTask.Factory<T> detTask;
	
	protected abstract Matrix<T> create(MatrixStore<T> store);

	@Override
	public NumericType supports() {
		return NumericType.COMPLEX_MATRIX;
	}

	@Override
	public NumericObject sqrt(NumericObject value) {
		return create(value, sqrtFunction);
	}

	@Override
	public NumericObject inv(NumericObject value) throws Exception {
		MatrixStore<T> data = process(value);
		return create(invTask.make(data).invert(data));
	}

	@Override
	public NumericObject sin(NumericObject value) {
		return create(value, sinFunction);
	}

	@Override
	public NumericObject det(NumericObject value) {
		
	}

	@Override
	public NumericObject tan(NumericObject value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumericObject cos(NumericObject value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private MatrixStore<T> process(NumericObject numericObject) {
		return ((OjalgoMatrix<T>) numericObject).getLazyStore();
	}
	
	protected NumericObject create(NumericObject value, UnaryFunction<T> function) {
		return create(process(value).operateOnAll(function));
	}
	
	@Required
	public void setSqrtFunction(UnaryFunction<T> sqrtFunction) {
		this.sqrtFunction = sqrtFunction;
	}

	@Required
	public void setSinFunction(UnaryFunction<T> sinFunction) {
		this.sinFunction = sinFunction;
	}

	@Required
	public void setTanFunction(UnaryFunction<T> tanFunction) {
		this.tanFunction = tanFunction;
	}

	@Required
	public void setCosFunction(UnaryFunction<T> cosFunction) {
		this.cosFunction = cosFunction;
	}
	
	@Required
	public void setInvTask(InverterTask.Factory<T> invTask) {
		this.invTask = invTask;
	}

	@Required
	public void setDetTask(DeterminantTask.Factory<T> detTask) {
		this.detTask = detTask;
	}
}
