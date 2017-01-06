package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorCode;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken;
import com.klab.interpreter.translate.factory.OperatorInstructionCodesFactory;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OperatorTranslateHandler extends AbstractTranslateHandler {
    private OperatorInstructionCodesFactory operatorInstructionCodesFactory;

    @Override
    public void handle(Expression<ParseToken> expression) {
        OperatorToken operatorToken = (OperatorToken) expression.getValue();
        if (OperatorCode.PLUS.equals(operatorToken.getOperatorCode())) {
            return;
        }
        if (OperatorCode.ASSIGN.equals(operatorToken.getOperatorCode())) {
            expression.setProperty(Expression.ANS_PROPERTY_KEY, false);
        }
        InstructionCode instructionCode = operatorInstructionCodesFactory.get(operatorToken.getOperatorCode());
        if (Objects.isNull(instructionCode)) {
            throw new RuntimeException();
        }
        Instruction instruction = new Instruction();
        instruction.setInstructionCode(instructionCode);
        tCM.addInstruction(instruction, address(expression));
    }

    @Override
    public ParseClass supportedParseClass() {
        return ParseClass.OPERATOR;
    }

    @Autowired
    public void setOperatorInstructionCodesFactory(OperatorInstructionCodesFactory operatorInstructionCodesFactory) {
        this.operatorInstructionCodesFactory = operatorInstructionCodesFactory;
    }
}
