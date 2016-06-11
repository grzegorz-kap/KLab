package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.exception.UnexpectedFunctionArgumentsDelimiter;
import com.klab.interpreter.parsing.handlers.helpers.StackHelper;
import com.klab.interpreter.parsing.model.ParseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.klab.interpreter.parsing.exception.UnexpectedFunctionArgumentsDelimiter.UNEXPECTED_ARGUMENT_DELIMITER;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FunctionArgumentDelimiterParseHandler extends AbstractParseHandler {
    private StackHelper stackHelper;

    @Override
    public TokenClass getSupportedTokenClass() {
        return null;
    }

    @Override
    public void handle() {
        if (!stackHelper.stackToExpressionUntilParseClass(parseContextManager, ParseClass.CALL_START)) {
            throw new UnexpectedFunctionArgumentsDelimiter(UNEXPECTED_ARGUMENT_DELIMITER, parseContextManager.getParseContext());
        }
        parseContextManager.incrementTokenPosition(1);
    }

    @Autowired
    public void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }
}
