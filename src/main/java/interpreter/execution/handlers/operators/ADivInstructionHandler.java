package interpreter.execution.handlers.operators;

import interpreter.translate.model.InstructionCode;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ADivInstructionHandler extends AbstractOperatorInstructionHandler {
	@Override
	protected NumericObject calculate(NumericObject a, NumericObject b, NumericType type) {
		return operatorFactory.getOperator(type).mult(convert(a, type), convert(b, type));
	}

	@Override
	public InstructionCode getSupportedInstructionCode() {
		return InstructionCode.ADIV;
	}
}
