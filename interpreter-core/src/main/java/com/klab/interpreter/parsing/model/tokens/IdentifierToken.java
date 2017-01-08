package com.klab.interpreter.parsing.model.tokens;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;

public class IdentifierToken extends ParseToken {
    private String id;
    private int address;

    public IdentifierToken(Token token) {
        super(token, ParseClass.IDENTIFIER);
        id = token.getLexeme();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMemAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
