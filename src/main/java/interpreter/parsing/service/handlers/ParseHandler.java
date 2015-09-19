package interpreter.parsing.service.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.service.ParseContextManager;

public interface ParseHandler {
    void handle();

    void handleStackFinish();

    void setContextManager(ParseContextManager parseContextManager);

    TokenClass getSupportedTokenClass();
}
