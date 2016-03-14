package com.klab.interpreter.parsing.service;

import com.klab.interpreter.parsing.handlers.ParseHandler;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.klab.interpreter.parsing.model.expression.Expression.PRINT_PROPERTY_KEY;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ParserService extends AbstractParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractParser.class);

    @Autowired
    public ParserService(Set<ParseHandler> parseHandlers, ParseContextManager parseContextManager) {
        super(parseHandlers, parseContextManager);
    }

    @Override
    public List<Expression<ParseToken>> process() {
        parseContext.setInstructionStop(false);
        parseContext.setInstructionPrint(true);
        parseContext.getExpression().clear();
        parseInstruction();
        finishParseStack();
        setFinishProperties();
        return parseContext.getExpression();
    }

    private void setFinishProperties() {
        boolean printFlag = parseContext.isInstructionPrint();
        parseContext.forEachExpression(parseTokenExpression -> parseTokenExpression.setProperty(PRINT_PROPERTY_KEY, printFlag));
    }

    private void parseInstruction() {
        while (!parseContext.isInstructionStop() && !parseContextManager.isEndOfTokens()) {
            ParseHandler parseHandler = getParseHandler(parseContext.getCurrentToken().getTokenClass());
            if (Objects.isNull(parseHandler)) {
                LOGGER.error("'{}'\t'{}'", parseContext.getCurrentToken().getLexeme(), parseContext.getCurrentToken().getTokenClass());
            }
            parseHandler.handle();
        }
    }

    private void finishParseStack() {
        while (parseContextManager.stackSize() > 0) {
            ParseHandler parseHandler = getParseHandler(parseContext.stackPeek().getTokenClass());
            parseHandler.handleStackFinish();
        }
    }
}
