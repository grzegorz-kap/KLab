package com.klab.interpreter.parsing.exception;

import com.klab.interpreter.parsing.model.ParseContext;

public class UnexpectedCloseParenthesisException extends ParseException {

    private static final long serialVersionUID = 1L;

    public UnexpectedCloseParenthesisException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
