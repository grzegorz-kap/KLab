package interpreter.service.functions.matrix.generation;

import org.springframework.stereotype.Component;

import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;

@Component
public class ZerosFunction extends AbstractMatrixGeneratorFunction {

	public ZerosFunction() {
		super(1, 2, InternalFunction.ZEROS_FUNCTION);
	}

	@Override
	public ObjectData call(ObjectData[] datas) {
		return createMatrix(datas, matrixFactory::createZerosDouble);
	}

}