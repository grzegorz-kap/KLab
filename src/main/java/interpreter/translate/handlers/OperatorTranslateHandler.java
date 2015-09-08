package interpreter.translate.handlers;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import interpreter.translate.factory.OperatorInstructionCodesFactory;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;

public class OperatorTranslateHandler extends AbstractTranslateHandler {

    private OperatorInstructionCodesFactory operatorInstructionCodesFactory;

    @Override
    public void handle(Expression<ParseToken> expression) {
        OperatorToken operatorToken = (OperatorToken) expression.getValue();
        InstructionCode instructionCode = operatorInstructionCodesFactory.get(operatorToken.getOperatorCode());
        Instruction instruction = new Instruction();
        instruction.setInstructionCode(instructionCode);
        translateContextManager.addInstruction(instruction);
    }

    public void setOperatorInstructionCodesFactory(OperatorInstructionCodesFactory operatorInstructionCodesFactory) {
        this.operatorInstructionCodesFactory = operatorInstructionCodesFactory;
    }
}
