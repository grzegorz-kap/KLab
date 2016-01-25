package interpreter.translate.handlers;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.IdentifierToken;
import interpreter.parsing.utils.ExpressionUtils;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.TokenIdentifierObject;
import interpreter.types.IdentifierObject;
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
        if (ExpressionUtils.isAssignmentTarget(expression)) {
            tCM.addInstruction(new Instruction(InstructionCode.PUSH, 0, new TokenIdentifierObject(identifierToken)));
        } else {
            addLoadInstruction(identifierToken, expression);
        }
    }

    private void addLoadInstruction(IdentifierToken identifierToken, Expression<ParseToken> expression) {
        Instruction instruction = new Instruction(InstructionCode.LOAD, 0);
        IdentifierObject identifierObject = new TokenIdentifierObject(identifierToken);
        identifierObject.setCanBeScript(Objects.isNull(expression.getParent()) && expression.getChildrenCount() == 0);
        instruction.add(identifierObject);
        tCM.addInstruction(instruction);
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.IDENTIFIER;
    }
}
