package interpreter.parsing.service;

import interpreter.parsing.service.handlers.ParseHandler;

public class ParserService extends AbstractParser {

    @Override
    protected void process() {
        while (!parseContextManager.endOfTokens()) {
            ParseHandler parseHandler = getParseHandler(parseContext.getCurrentToken().getTokenClass());
            parseHandler.handle();
        }
        while (parseContextManager.stackSize() > 0) {
            ParseHandler parseHandler = getParseHandler(parseContext.stackPeek().getTokenClass());
            parseHandler.handleStackFinish();
        }
    }
}
