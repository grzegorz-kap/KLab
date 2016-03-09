package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class ForToken extends ParseToken {
    public ForToken(Token token) {
        super(token, ParseClass.FOR_KEYWORD);
    }
}
