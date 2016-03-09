package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class MatrixStartToken extends ParseToken {
    public MatrixStartToken(Token token) {
        super(token, ParseClass.MATRIX_START);
    }
}
