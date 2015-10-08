package interpreter.parsing.service;

import interpreter.parsing.handlers.ParseHandler;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
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
    public ParserService(Set<ParseHandler> parseHandlers, ParseContextManager parseContextManager) {
        super(parseHandlers, parseContextManager);
    }

    @Override
    public Expression<ParseToken> process() {
        parseContext.setInstructionStop(false);
        parseInstruction();
        clearExecutionStack();
        return parseContext.getLastFromExpression(1).get(0);
    }

    private void parseInstruction() {
        while (!parseContext.isInstructionStop() && !parseContextManager.isEndOfTokens()) {
            ParseHandler parseHandler = getParseHandler(parseContext.getCurrentToken().getTokenClass());
            isUnsupported(parseHandler);
            parseHandler.handle();
        }
    }

    private void clearExecutionStack() {
        while (parseContextManager.stackSize() > 0) {
            ParseHandler parseHandler = getParseHandler(parseContext.stackPeek().getTokenClass());
            parseHandler.handleStackFinish();
        }
    }

    private void isUnsupported(final ParseHandler parseHandler) {
        if (Objects.isNull(parseHandler)) {
            LOGGER.error("'{}'\t'{}'", parseContext.getCurrentToken().getLexeme(), parseContext.getCurrentToken().getTokenClass());
        }
    }
}
