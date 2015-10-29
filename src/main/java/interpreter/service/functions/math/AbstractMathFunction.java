package interpreter.service.functions.math;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import interpreter.commons.exception.IllegalArgumentException;
import interpreter.service.functions.AbstractInternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;

public abstract class AbstractMathFunction extends AbstractInternalFunction {

	@Autowired
	protected MathFunctionsHolder functionsHolder;

	public AbstractMathFunction(int argsMin, int argsMax, String name) {
		super(argsMin, argsMax, name);
	}

	protected abstract ObjectData process(NumericObject[] datas);

	@Override
	public ObjectData call(ObjectData[] datas) {
		return process(Stream.of(datas).map(this::mapToNumeric).toArray(size -> new NumericObject[size]));
	}

	protected NumericObject mapToNumeric(ObjectData data) {
		if (!(data instanceof NumericObject)) {
			throw new IllegalArgumentException("Numeric object expected");

		}
		return (NumericObject) data;
	}

}
