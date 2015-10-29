package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class EndifToken extends ParseToken {

    public EndifToken(Token token) {
        setToken(token);
        setParseClass(ParseClass.END_IF);
    }
}