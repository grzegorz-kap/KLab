package interpreter.parsing.service.handlers.helpers;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.service.ParseContextManager;
import org.springframework.stereotype.Component;

@Component
public class StackHelper {

    public boolean stackToExpressionUntilTokenClass(ParseContextManager parseContextManager, ParseClass parseClass) {
        while (!parseContextManager.isStackEmpty() && !parseContextManager.stackPeekClass().equals(parseClass)) {
            callStackFinishHandler(parseContextManager);
        }
        return !parseContextManager.isStackEmpty();
    }

    private void callStackFinishHandler(ParseContextManager parseContextManager) {
        parseContextManager.getParseHandler(parseContextManager.stackPeekTokenClass()).handleStackFinish();
    }
}
