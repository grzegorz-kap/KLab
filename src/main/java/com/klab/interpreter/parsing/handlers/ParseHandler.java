package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.service.ParseContextManager;

public interface ParseHandler {
    void handle();

    void handleStackFinish();

    void setContextManager(ParseContextManager parseContextManager);

    TokenClass getSupportedTokenClass();
}
