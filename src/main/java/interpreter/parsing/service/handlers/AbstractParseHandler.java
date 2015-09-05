package interpreter.parsing.service.handlers;

import interpreter.parsing.service.Parser;

public abstract class AbstractParseHandler implements ParseHandler {

    private Parser parser;


    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
