package interpreter.parsing.service;

import interpreter.parsing.service.handlers.ParseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ParserService extends AbstractParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractParser.class);

    @Autowired
    public ParserService(Set<ParseHandler> parseHandlers) {
        super(parseHandlers);
    }

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
