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
import static java.util.Objects.nonNull;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ParserServiceImpl implements ParserService, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserServiceImpl.class);
    private ParseContext parseContext;
    private ParseContextManager parseContextManager;
    private ParseHandler[] parseHandlers = new ParseHandler[TokenClass.values().length];

    @Override
    public List<Expression<ParseToken>> process() {
        beforeProcessing();
        while (!parseContext.isInstructionStop() && !parseContextManager.isEndOfTokens()) {
            TokenClass tokenClass = parseContext.getCurrentToken().getTokenClass();
            ParseHandler parseHandler = parseHandlers[tokenClass.getIndex()];
            parseHandler.handle();
        }
        return afterProcessing();
    }

    @Autowired
    void setParseHandlers(Set<ParseHandler> parseHandlers) {
        parseHandlers.stream()
                .filter(parseHandler -> nonNull(parseHandler.supportedTokenClass()))
                .forEach(parseHandler -> {
                    this.parseHandlers[parseHandler.supportedTokenClass().getIndex()] = parseHandler;
                });
    }

    @Override
    public boolean hasNext() {
        return !parseContextManager.isEndOfTokens();
    }

    private List<Expression<ParseToken>> afterProcessing() {
        finishParseStack();
        setFinishProperties();
        return parseContext.getExpression();
    }

    private void beforeProcessing() {
        parseContext.setInstructionStop(false);
        parseContext.setInstructionPrint(true);
        parseContext.getExpression().clear();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Stream.of(parseHandlers).filter(Objects::nonNull)
                .forEach(handler -> {
                    handler.setParseContextManager(parseContextManager);
                });
    }

    @Override
    public void setupTokenList(TokenList tokenList) {
        parseContext = new ParseContext(tokenList);
        parseContext.setParseHandlers(parseHandlers);
        parseContextManager.setParseContext(parseContext);
    }

    private void setFinishProperties() {
        boolean printFlag = parseContext.isInstructionPrint();
        parseContext.forEachExpression(parseTokenExpression -> parseTokenExpression.setProperty(PRINT_PROPERTY_KEY, printFlag));
    }

    private void finishParseStack() {
        while (parseContextManager.stackSize() > 0) {
            ParseHandler parseHandler = parseHandlers[parseContext.stackPeek().getTokenClass().getIndex()];
            parseHandler.handleStackFinish();
        }
    }

    @Autowired
    void setParseContextManager(ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
    }
}
