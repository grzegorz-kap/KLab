package interpreter.translate.handlers;

import interpreter.math.NumberScalarFactory;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.NumberToken;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;

public class NumberTranslateHandler extends AbstractTranslateHandler implements TranslateHandler {

    private NumberScalarFactory numberScalarFactory;

    @Override
    public void handle(Expression<ParseToken> expression) {
        NumberToken numberToken = (NumberToken) expression.getValue();
        Instruction instruction = new Instruction();
        instruction.setInstructionCode(InstructionCode.PUSH_DOUBLE);
        instruction.setArgumentsNumber(1);
        instruction.add(numberScalarFactory.getDouble(format(numberToken)));
        translateContextManager.addInstruction(instruction);
    }

    private Double format(NumberToken numberToken) {
        return Double.valueOf(numberToken.getToken().getLexeme());
    }

    public void setNumberScalarFactory(NumberScalarFactory numberScalarFactory) {
        this.numberScalarFactory = numberScalarFactory;
    }
}
