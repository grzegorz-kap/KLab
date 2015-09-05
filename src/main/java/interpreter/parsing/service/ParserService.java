package interpreter.parsing.service;

import interpreter.parsing.service.handlers.ParseHandler;

import java.util.Objects;

public class ParserService extends AbstractParser {

    @Override
    protected void process() {
        while (!parseContextManager.endOfTokens()) {
            ParseHandler parseHandler = getParseHandler(parseContext.getCurrentToken().getTokenClass());
            if(Objects.isNull(parseHandler)){
                throw new RuntimeException();
            }
            parseHandler.handle();
        }
    }
}
