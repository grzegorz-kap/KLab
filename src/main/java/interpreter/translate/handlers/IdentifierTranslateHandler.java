package interpreter.translate.handlers;

import interpreter.math.IdentifierObject;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.IdentifierToken;
import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IdentifierTranslateHandler extends AbstractTranslateHandler {

    @Override
    public void handle(Expression<ParseToken> expression) {
        IdentifierToken identifierToken = (IdentifierToken) expression.getValue();
        if (isAssignmentTarget(expression)) {
            addAddressPointer(identifierToken);
        } else {
            addLoadInstruction(identifierToken);
        }
    }

    private void addAddressPointer(IdentifierToken identifierToken) {
        Instruction instruction = new Instruction(InstructionCode.PUSH, 0);
        instruction.add(new IdentifierObject(identifierToken));
        translateContextManager.addInstruction(instruction);
    }

    private void addLoadInstruction(IdentifierToken identifierToken) {
        Instruction instruction = new Instruction(InstructionCode.LOAD, 0);
        instruction.add(new IdentifierObject(identifierToken));
        translateContextManager.addInstruction(instruction);
    }

    private boolean isAssignmentTarget(Expression<ParseToken> expression) {
        Expression<ParseToken> parent = expression.getParent();
        if (isParentNull(parent)) return false;
        ParseToken parentToken = parent.getValue();
        if (isNotOperatorToken(parentToken)) return false;
        OperatorToken operatorToken = (OperatorToken) parentToken;
        return !isNotAssignOperator(operatorToken) && !isNotParentLeftChild(expression, parent);
    }

    private boolean isNotParentLeftChild(Expression<ParseToken> expression, Expression<ParseToken> parent) {
        return parent.getChildren().get(0) != expression;
    }

    private boolean isNotAssignOperator(OperatorToken operatorToken) {
        return !operatorToken.getOperatorCode().equals(OperatorCode.ASSIGN);
    }

    private boolean isNotOperatorToken(ParseToken parentToken) {
        return !(parentToken instanceof OperatorToken);
    }

    private boolean isParentNull(Expression<ParseToken> parent) {
        return Objects.isNull(parent);
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.IDENTIFIER;
    }
}
