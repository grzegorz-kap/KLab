package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class EndForToken extends ParseToken {
    public EndForToken(Token token) {
        setToken(token);
        setParseClass(ParseClass.ENDFOR_KEYWORD);
    }
}
