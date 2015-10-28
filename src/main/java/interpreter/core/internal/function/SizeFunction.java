package interpreter.core.internal.function;

import org.springframework.stereotype.Component;

@Component
public class SizeFunction extends AbstractInternalFunction implements InternalFunction {

	public SizeFunction() {
		super(null, 1, InternalFunction.SIZE_FUNCTION);
	}
}
