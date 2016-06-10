package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.service.ParseContextManager;

public interface ParseHandler {
    void handle();

    default void handleStackFinish() {
        throw new UnsupportedOperationException();
    }

    void setContextManager(ParseContextManager parseContextManager);

    TokenClass getSupportedTokenClass();
}
