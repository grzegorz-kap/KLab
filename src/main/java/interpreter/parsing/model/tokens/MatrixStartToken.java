package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseToken;

public class MatrixStartToken extends ParseToken {

    public MatrixStartToken() {
    }

    public MatrixStartToken(Token token) {
        setToken(token);
    }
}
