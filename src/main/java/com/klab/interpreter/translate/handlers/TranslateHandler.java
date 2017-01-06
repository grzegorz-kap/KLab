package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.service.TranslateContextManager;

public interface TranslateHandler {
    void handle(Expression<ParseToken> expression);

    void setTranslateContextManager(TranslateContextManager translateContextManager);

    ParseClass supportedParseClass();
}
