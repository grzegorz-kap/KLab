package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InstructionEndHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        parseContextManager.setInstructionStop(true);
        setupPrintProperty();
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return null;
    }

    private void setupPrintProperty() {
        TokenClass currentClass = parseContextManager.tokenAt(0).getTokenClass();
        parseContextManager.setInstructionPrint(isExpressionShouldBePrinted(currentClass));
    }

    private boolean isExpressionShouldBePrinted(TokenClass currentClass) {
        return currentClass.equals(TokenClass.COMMA) || currentClass.equals(TokenClass.NEW_LINE);
    }
}
