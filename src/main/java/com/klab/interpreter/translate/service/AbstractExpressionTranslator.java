package com.klab.interpreter.translate.service;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorCode;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken;
import com.klab.interpreter.translate.handlers.TranslateHandler;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.model.TranslateContext;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractExpressionTranslator implements ExpressionTranslator {
    protected Code code;
    protected IdentifierMapper identifierMapper;

    TranslateContext translateContext;
    TranslateContextManager manager = new TranslateContextManager();

    private TranslateHandler[] translateHandlers;

    protected abstract void translate();

    boolean checkIfOperator(OperatorCode operatorCode, ParseToken parseToken) {
        return parseToken instanceof OperatorToken && operatorCode.equals(((OperatorToken) parseToken).getOperatorCode());
    }

    @Override
    public MacroInstruction translate(Expression<ParseToken> parseTokenExpression) {
        translateContext = new TranslateContext(parseTokenExpression);
        manager.setTranslateContext(translateContext);
        translate();
        return translateContext.getMacroInstruction();
    }

    @Override
    public TranslateHandler getTranslateHandler(ParseClass parseClass) {
        return translateHandlers[parseClass.getIndex()];
    }

    @Override
    public void setCode(Code code) {
        this.code = code;
    }


    @Autowired
    public void setTranslateHandlers(TranslateHandler[] translateHandlers) {
        this.translateHandlers = new TranslateHandler[ParseClass.values().length];
        for (TranslateHandler translateHandler : translateHandlers) {
            translateHandler.setTranslateContextManager(manager);
            this.translateHandlers[translateHandler.getSupportedParseClass().getIndex()] = translateHandler;
        }
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }
}
