package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class ParenthesisParseToken extends ParseToken {
    public ParenthesisParseToken(Token token, ParseClass parseClass) {
        super(token, parseClass);
    }
}
