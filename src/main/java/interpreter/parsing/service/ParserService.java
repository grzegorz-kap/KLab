package interpreter.parsing.service;

import interpreter.parsing.service.handlers.ParseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ParserService extends AbstractParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractParser.class);

    @Override
    protected void process() {
        while (!parseContextManager.endOfTokens()) {
            ParseHandler parseHandler = getParseHandler(parseContext.getCurrentToken().getTokenClass());
            iSUnsupported(parseHandler);
            parseHandler.handle();
        }
        while (parseContextManager.stackSize() > 0) {
            ParseHandler parseHandler = getParseHandler(parseContext.stackPeek().getTokenClass());
            parseHandler.handleStackFinish();
        }
    }

    private void iSUnsupported(final ParseHandler parseHandler) {
        if (Objects.isNull(parseHandler)) {
            LOGGER.error("'{}'\t'{}'", parseContext.getCurrentToken().getLexeme(), parseContext.getCurrentToken().getTokenClass());
        }
    }
}
