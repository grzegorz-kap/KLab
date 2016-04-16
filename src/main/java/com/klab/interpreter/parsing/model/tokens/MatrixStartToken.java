package com.klab.interpreter.parsing.model.tokens;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;

public class MatrixStartToken extends ParseToken {
    public MatrixStartToken(Token token) {
        super(token, ParseClass.MATRIX_START);
    }
}
