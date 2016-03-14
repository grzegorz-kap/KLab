package com.klab.interpreter.parsing.model.tokens;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.types.NumericType;

public class NumberToken extends ParseToken {
    private NumericType numericType;

    public NumberToken(Token token, NumericType numericType) {
        super(token, ParseClass.NUMBER);
        this.numericType = numericType;
    }

    public NumericType getNumericType() {
        return numericType;
    }
}
