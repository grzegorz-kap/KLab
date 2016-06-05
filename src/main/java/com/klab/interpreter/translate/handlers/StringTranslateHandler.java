package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.StringData;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StringTranslateHandler extends AbstractTranslateHandler {
    @Override
    public void handle(Expression<ParseToken> expression) {
        String value = expression.getValue().getToken().getLexeme();
        Instruction instruction = new Instruction(InstructionCode.PUSH, 1, new StringData(value));
        tCM.addInstruction(instruction, address(expression));
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.STRING;
    }
}
