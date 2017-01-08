package com.klab.interpreter.lexer.utils;

import com.klab.interpreter.lexer.model.TokenClass;

import java.util.HashMap;
import java.util.Map;

public abstract class SymbolsMapper {

    private static final Map<String, TokenClass> TOKEN_CLASS_MAP = new HashMap<String, TokenClass>() {{
        put(",", TokenClass.COMMA);
        put(":", TokenClass.COLON);
        put(";", TokenClass.SEMICOLON);
        put("(", TokenClass.OPEN_PARENTHESIS);
        put(")", TokenClass.CLOSE_PARENTHESIS);
        put("[", TokenClass.OPEN_BRACKET);
        put("]", TokenClass.CLOSE_BRACKET);
        put(":", TokenClass.MATRIX_ALL);
    }};

    public static TokenClass getTokenClass(String symbol) {
        return TOKEN_CLASS_MAP.get(symbol);
    }
}
