package interpreter.parsing.service.handlers;

import interpreter.parsing.service.Parser;

public interface ParseHandler {
    void handle();
    void setParser(Parser parser);
}
