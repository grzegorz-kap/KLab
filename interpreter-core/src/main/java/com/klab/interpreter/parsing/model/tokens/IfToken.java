package com.klab.interpreter.parsing.model.tokens;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;

public class IfToken extends ParseToken {
    public IfToken(Token token) {
        super(token, ParseClass.IF);
    }
}
