package interpreter.service.functions.math;

import org.springframework.beans.factory.annotation.Autowired;

import interpreter.service.functions.AbstractInternalFunction;

public abstract class AbstractMathFunction extends AbstractInternalFunction {

	@Autowired
	protected MathFunctionsHolder functionsHolder;

	public AbstractMathFunction(int argsMin, int argsMax, String name) {
		super(argsMin, argsMax, name);
	}

}
