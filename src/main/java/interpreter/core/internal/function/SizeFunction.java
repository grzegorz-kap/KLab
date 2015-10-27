package interpreter.core.internal.function;

import org.springframework.stereotype.Component;

@Component
public class SizeFunction implements InternalFunction {

	@Override
	public String getName() {
		return InternalFunction.SIZE_FUNCTION;
	}

	@Override
	public int getArgumentsNumber() {
		return 1;
	}

}
