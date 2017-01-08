package com.klab.interpreter.parsing.model.tokens;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;

public class EndForToken extends ParseToken {
    public EndForToken(Token token) {
        super(token, ParseClass.ENDFOR_KEYWORD);
    }
}
