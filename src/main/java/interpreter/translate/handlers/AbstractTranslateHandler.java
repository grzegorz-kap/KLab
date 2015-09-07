package interpreter.translate.handlers;

import interpreter.translate.service.TranslateContextManager;

public abstract class AbstractTranslateHandler {

    protected TranslateContextManager translateContextManager;

    public void setTranslateContextManager(TranslateContextManager translateContextManager) {
        this.translateContextManager = translateContextManager;
    }
}
