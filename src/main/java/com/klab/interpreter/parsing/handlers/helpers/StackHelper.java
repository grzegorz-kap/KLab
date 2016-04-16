package com.klab.interpreter.parsing.handlers.helpers;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.service.ParseContextManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StackHelper {

    public boolean stackToExpressionUntilParseClass(ParseContextManager parseContextManager, ParseClass parseClass) {
        while (!parseContextManager.isStackEmpty() && !parseContextManager.stackPeekClass().equals(parseClass)) {
            callStackFinishHandler(parseContextManager);
        }
        return !parseContextManager.isStackEmpty();
    }

    public boolean stackToExpressionUntilParseClass(ParseContextManager parseContextManager, ParseClass[] parseClasses) {
        while (!parseContextManager.isStackEmpty() && !contains(parseClasses, parseContextManager.stackPeekClass())) {
            callStackFinishHandler(parseContextManager);
        }
        return !parseContextManager.isStackEmpty();
    }

    private boolean contains(ParseClass[] parseClasses, ParseClass parseClass) {
        return Arrays.stream(parseClasses).filter(parseClass::equals).findFirst().orElse(null) != null;
    }

    private void callStackFinishHandler(ParseContextManager parseContextManager) {
        parseContextManager.getParseHandler(parseContextManager.stackPeekTokenClass()).handleStackFinish();
    }
}
