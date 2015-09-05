package interpreter.parsing.service.handlers;

import interpreter.parsing.service.ParseContextManager;

public interface ParseHandler {
    void handle();

    void setContextManager(ParseContextManager parseContextManager);
}
