package com.klab.interpreter.parsing.model.tokens;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;

public class ParenthesisParseToken extends ParseToken {
    public ParenthesisParseToken(Token token, ParseClass parseClass) {
        super(token, parseClass);
    }
}
