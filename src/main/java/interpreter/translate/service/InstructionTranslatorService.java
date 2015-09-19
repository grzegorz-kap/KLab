package interpreter.translate.service;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionValue;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;


public class InstructionTranslatorService extends AbstractInstructionTranslator {

    @Override
    protected void translate() {
        Expression<ParseToken> parseTokenExpression = translateContext.getExpression();
        process(parseTokenExpression);
        addPrint();
    }

    private void translateExpressionValue(Expression<ParseToken> expression) {
        getTranslateHandler(expression.getValue().getTokenClass()).handle(expression);
    }

    private void translateExpressionNode(Expression<ParseToken> expression) {
        expression.getChildren().forEach(this::process);
        translateExpressionValue(expression);
    }

    private void process(Expression<ParseToken> parseTokenExpression) {
        if (parseTokenExpression instanceof ExpressionValue) {
            translateExpressionValue(parseTokenExpression);
        } else {
            translateExpressionNode(parseTokenExpression);
        }
    }

    private void addPrint() {
        translateContextManager.addInstruction(new Instruction(InstructionCode.PRINT, 0));
    }
}
