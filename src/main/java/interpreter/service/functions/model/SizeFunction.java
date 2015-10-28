package interpreter.service.functions.model;

import org.springframework.stereotype.Component;

import interpreter.core.internal.function.AbstractInternalFunction;

@Component
public class SizeFunction extends AbstractInternalFunction implements InternalFunction {

	public SizeFunction() {
		super(null, 1, InternalFunction.SIZE_FUNCTION);
	}
}
