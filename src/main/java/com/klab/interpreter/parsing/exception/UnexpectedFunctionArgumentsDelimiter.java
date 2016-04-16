package com.klab.interpreter.parsing.exception;

import com.klab.interpreter.parsing.model.ParseContext;

public class UnexpectedFunctionArgumentsDelimiter extends ParseException {

    public static final String UNEXPECTED_ARGUMENT_DELIMITER = "Function arguments delimiter found outside function call";
    private static final long serialVersionUID = 1L;

    public UnexpectedFunctionArgumentsDelimiter(String message, ParseContext parseContext) {
        super(message, parseContext);
    }

}
