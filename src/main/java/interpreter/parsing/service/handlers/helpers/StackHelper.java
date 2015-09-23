package interpreter.parsing.service.handlers.helpers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.service.ParseContextManager;
import org.springframework.stereotype.Component;

@Component
public class StackHelper {

    public boolean stackToExpressionUntilTokenClass(ParseContextManager parseContextManager, TokenClass tokenClass) {
        while (!parseContextManager.isStackEmpty() && !parseContextManager.stackPeekClass().equals(tokenClass)) {
            parseContextManager.getParseHandler(parseContextManager.stackPeekClass()).handleStackFinish();
        }
        return !parseContextManager.isStackEmpty();
    }
}
