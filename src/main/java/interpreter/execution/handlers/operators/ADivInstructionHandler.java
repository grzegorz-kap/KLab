package interpreter.execution.handlers.operators;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.translate.model.InstructionCode;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;

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
