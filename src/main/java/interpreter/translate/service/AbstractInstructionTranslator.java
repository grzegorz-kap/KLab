package interpreter.translate.service;

import java.util.Set;

import interpreter.execution.model.Code;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.handlers.TranslateHandler;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.model.TranslateContext;

public abstract class AbstractInstructionTranslator implements InstructionTranslator {
    protected TranslateContext translateContext;
    protected TranslateContextManager translateContextManager = new TranslateContextManager();
    protected Code code;
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
