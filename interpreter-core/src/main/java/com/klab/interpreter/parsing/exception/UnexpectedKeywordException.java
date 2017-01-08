package com.klab.interpreter.parsing.exception;

import com.klab.interpreter.parsing.model.ParseContext;

public class UnexpectedKeywordException extends ParseException {
    public UnexpectedKeywordException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
