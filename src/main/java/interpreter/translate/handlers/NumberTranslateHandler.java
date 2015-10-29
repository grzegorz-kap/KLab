package interpreter.translate.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.NumberToken;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.scalar.NumberScalarFactory;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NumberTranslateHandler extends AbstractTranslateHandler {

	private NumberScalarFactory numberScalarFactory;

	@Override
	public void handle(Expression<ParseToken> expression) {
		NumberToken numberToken = (NumberToken) expression.getValue();
		Instruction instruction = new Instruction();
		instruction.setInstructionCode(InstructionCode.PUSH);
		instruction.setArgumentsNumber(1);
		instruction.add(numberScalarFactory.getDouble(numberToken.getToken().getLexeme()));
		translateContextManager.addInstruction(instruction);
	}

	@Override
	public ParseClass getSupportedParseClass() {
		return ParseClass.NUMBER;
	}

	@Autowired
	public void setNumberScalarFactory(NumberScalarFactory numberScalarFactory) {
		this.numberScalarFactory = numberScalarFactory;
	}
}
