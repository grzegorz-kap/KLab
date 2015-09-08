package interpreter.translate.handlers;

import interpreter.translate.service.TranslateContextManager;

public abstract class AbstractTranslateHandler implements TranslateHandler {

    protected TranslateContextManager translateContextManager;

    @Override
    public void setTranslateContextManager(TranslateContextManager translateContextManager) {
        this.translateContextManager = translateContextManager;
    }
}
