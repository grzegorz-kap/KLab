package com.klab.interpreter.translate.service;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.handlers.TranslateHandler;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.model.TranslateContext;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractExpressionTranslator implements ExpressionTranslator {
    protected TranslateContext translateContext;
    protected TranslateContextManager manager = new TranslateContextManager();
    protected Code code;
    protected IdentifierMapper identifierMapper;
    private TranslateHandler[] translateHandlers;

    protected abstract void translate();

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
