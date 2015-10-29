package interpreter.service.functions.size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import interpreter.service.functions.AbstractInternalFunction;
import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;
import interpreter.types.Sizeable;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixFactory;

@Component
public class SizeFunction extends AbstractInternalFunction {

	@Autowired
	private MatrixFactory matrixFactory;

	public SizeFunction() {
		super(1, 1, InternalFunction.SIZE_FUNCTION);
	}

	@Override
	public ObjectData call(ObjectData[] datas) {
		Matrix<Double> matrix = matrixFactory.createDouble(1, 2);
		Sizeable sizeable = (Sizeable) datas[0];
		matrix.set(0, 0, (double) sizeable.getRows());
		matrix.set(0, 1, (double) sizeable.getColumns());
		return matrix;
	}
}