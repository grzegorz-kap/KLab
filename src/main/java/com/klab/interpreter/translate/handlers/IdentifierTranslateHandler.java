package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.tokens.IdentifierToken;
import com.klab.interpreter.parsing.utils.ExpressionUtils;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.IdentifierObject;
import com.klab.interpreter.types.TokenIdentifierObject;
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
            tCM.addInstruction(new Instruction(InstructionCode.PUSH, 0, new TokenIdentifierObject(identifierToken)), address(expression));
        } else {
            addLoadInstruction(identifierToken, expression);
        }
    }

    private void addLoadInstruction(IdentifierToken identifierToken, Expression<ParseToken> expression) {
        Instruction instruction = new Instruction(InstructionCode.LOAD, 0);
        IdentifierObject identifierObject = new TokenIdentifierObject(identifierToken);
        identifierObject.setCanBeScript(Objects.isNull(expression.getParent()) && expression.getChildrenCount() == 0);
        instruction.add(identifierObject);
        tCM.addInstruction(instruction, address(expression));
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.IDENTIFIER;
    }
}
