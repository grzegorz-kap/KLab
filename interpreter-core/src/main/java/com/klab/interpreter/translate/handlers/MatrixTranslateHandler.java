package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixTranslateHandler extends AbstractTranslateHandler {

    @Override
    public void handle(Expression<ParseToken> expression) {
        Instruction instruction = new Instruction();
        instruction.setArgumentsNumber(expression.getChildrenCount());
        instruction.setInstructionCode(InstructionCode.MATRIX);
        tCM.addInstruction(instruction, address(expression));
    }

    @Override
    public ParseClass supportedParseClass() {
        return ParseClass.MATRIX;
    }
}
