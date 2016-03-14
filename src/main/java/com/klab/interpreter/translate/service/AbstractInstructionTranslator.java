package com.klab.interpreter.translate.service;

import com.klab.interpreter.commons.IdentifierMapper;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.handlers.TranslateHandler;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.model.TranslateContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public abstract class AbstractInstructionTranslator implements InstructionTranslator {
    protected TranslateContext translateContext;
    protected TranslateContextManager translateContextManager = new TranslateContextManager();
    protected Code code;
    @Autowired
    protected IdentifierMapper identifierMapper;
    private TranslateHandler[] translateHandlers = new TranslateHandler[ParseClass.values().length];

    public AbstractInstructionTranslator(Set<TranslateHandler> translateHandlers) {
        translateHandlers.forEach(this::addTranslateHandler);
    }

    protected abstract void translate();

    @Override
    public MacroInstruction translate(Expression<ParseToken> parseTokenExpression) {
        translateContext = new TranslateContext(parseTokenExpression);
        translateContextManager.setTranslateContext(translateContext);
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

    private void addTranslateHandler(TranslateHandler translateHandler) {
        translateHandler.setTranslateContextManager(translateContextManager);
        translateHandlers[translateHandler.getSupportedParseClass().getIndex()] = translateHandler;
    }
}
