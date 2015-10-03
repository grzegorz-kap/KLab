package interpreter.translate.handlers;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.service.TranslateContextManager;

public interface TranslateHandler {
    void handle(Expression<ParseToken> expression);

    void setTranslateContextManager(TranslateContextManager translateContextManager);

    ParseClass getSupportedParseClass();
}
