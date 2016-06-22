package com.klab.interpreter.parsing.service;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.lexer.model.TokenList;
import com.klab.interpreter.parsing.handlers.ParseHandler;
import com.klab.interpreter.parsing.model.ParseContext;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static com.klab.interpreter.parsing.model.expression.Expression.PRINT_PROPERTY_KEY;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ParserServiceImpl implements ParserService, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserServiceImpl.class);

    private ParseContext parseContext;
    private ParseContextManager parseContextManager;
    private ParseHandler[] parseHandlers = new ParseHandler[TokenClass.values().length];

    @Override
    public void afterPropertiesSet() throws Exception {
        Stream.of(parseHandlers).filter(Objects::nonNull)
                .forEach(handler -> {
                    handler.setParseContextManager(parseContextManager);
                });
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

    @Override
    public void setupTokenList(TokenList tokenList) {
        parseContext = new ParseContext(tokenList);
        parseContext.setParseHandlers(parseHandlers);
        parseContextManager.setParseContext(parseContext);
    }

    @Override
    public boolean hasNext() {
        return !parseContextManager.isEndOfTokens();
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

    private ParseHandler getParseHandler(TokenClass tokenClass) {
        return parseHandlers[tokenClass.getIndex()];
    }

    @Autowired
    public void setParseContextManager(ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
    }

    @Autowired
    public void setParseHandlers(Set<ParseHandler> parseHandlers) {
        parseHandlers.stream()
                .filter(parseHandler -> Objects.nonNull(parseHandler.getSupportedTokenClass()))
                .forEach(parseHandler -> {
                    this.parseHandlers[parseHandler.getSupportedTokenClass().getIndex()] = parseHandler;
                });
    }
}
