package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class IfToken extends ParseToken {

    public IfToken(Token token) {
        setToken(token);
        setParseClass(ParseClass.IF);
    }
}
