package com.klab.interpreter.parsing.model;

import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;

import java.util.Objects;

public class ParseToken {
    private Token token;
    private ParseClass parseClass;

    public ParseToken(Token token, ParseClass parseClass) {
        this.token = Objects.requireNonNull(token);
        this.parseClass = parseClass;
    }

    public CodeAddress getAddress() {
        return token.getAddress();
    }

    public Token getToken() {
        return token;
    }

    public TokenClass getTokenClass() {
        return token.getTokenClass();
    }

    public ParseClass getParseClass() {
        return parseClass;
    }

    public void setParseClass(ParseClass parseClass) {
        this.parseClass = parseClass;
    }

    public String getLexeme() {
        return token == null ? "" : token.getLexeme();
    }
}
