package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseToken;

public class VerseToken extends ParseToken {

    public VerseToken(Token token) {
        setToken(token);
    }
}
