package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.tokens.NumberToken;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.scalar.NumberScalarFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        tCM.addInstruction(instruction, address(expression));
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
