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
import interpreter.service.functions.InternalFunction;

@Component
public class MathFunctionsHolder {

	private List<MathFunctions> mathFunctions;

	public MathFunctions get(NumericType numericType, InternalFunction internalFunction) {
		MathFunctions functions = mathFunctions.get(numericType.getIndex());
		if (isNull(functions)) {
			throw new IllegalArgumentException(internalFunction.getName() + " not supported for " + numericType);
		}
		return functions;
	}

	@Autowired
	private void setMathFunctions(Set<MathFunctions> mathFunctions) {
		this.mathFunctions = new ArrayList<>(Collections.nCopies(NumericType.values().length, null));
		mathFunctions.forEach(function -> {
			this.mathFunctions.set(function.supports().getIndex(), function);
		});
	}

}
