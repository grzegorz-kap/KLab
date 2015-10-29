package interpreter.service.functions.math;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import interpreter.commons.exception.IllegalArgumentException;
import interpreter.parsing.model.NumericType;
import interpreter.service.functions.AbstractInternalFunction;
import interpreter.service.functions.InternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;

@Component
public class SqrtInternalFunction extends AbstractInternalFunction {

	private List<MathFunctions> mathFunctions;

	public SqrtInternalFunction() {
		super(1, 1, InternalFunction.SQRT_FUNCTION);
	}

	@Override
	public ObjectData call(ObjectData[] datas) {
		if (datas[0] instanceof NumericObject) {
			NumericObject numericObject = (NumericObject) datas[0];
			MathFunctions functions = mathFunctions.get(numericObject.getNumericType().getIndex());
			if (isNull(functions)) {
				throw new IllegalArgumentException(getName() + " not supported for " + numericObject.getNumericType());
			}
			return functions.sqrt(numericObject);
		} else {
			throw new IllegalArgumentException("Numeric object expected");
		}
	}

	@Autowired
	private void setMathFunctions(Set<MathFunctions> mathFunctions) {
		this.mathFunctions = new ArrayList<>(Collections.nCopies(NumericType.values().length, null));
		mathFunctions.forEach(function -> {
			this.mathFunctions.set(function.supports().getIndex(), function);
		});
	}

}
