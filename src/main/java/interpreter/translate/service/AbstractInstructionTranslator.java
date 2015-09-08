package interpreter.translate.service;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.handlers.TranslateHandler;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.model.TranslateContext;

import java.util.EnumMap;
import java.util.Map;

public abstract class AbstractInstructionTranslator implements InstructionTranslator {

    protected TranslateContext translateContext;
    protected TranslateContextManager translateContextManager;
    private Map<TokenClass, TranslateHandler> handlerMap = new EnumMap<>(TokenClass.class);

    @Override
    public MacroInstruction translate(Expression<ParseToken> parseTokenExpression) {
        translateContext = new TranslateContext(parseTokenExpression);
        translateContextManager.setTranslateContext(translateContext);
        translate();
        return translateContext.getMacroInstruction();
    }

    @Override
    public void addTranslateHandler(TokenClass tokenClass, TranslateHandler translateHandler) {
        translateHandler.setTranslateContextManager(translateContextManager);
        handlerMap.put(tokenClass, translateHandler);
    }

    public void setTranslateContextManager(TranslateContextManager translateContextManager) {
        this.translateContextManager = translateContextManager;
    }

    @Override
    public TranslateHandler getTranslateHandler(TokenClass tokenClass) {
        return handlerMap.get(tokenClass);
    }

    protected abstract void translate();
}
