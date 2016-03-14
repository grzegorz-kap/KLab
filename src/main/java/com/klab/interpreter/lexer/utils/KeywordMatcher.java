package com.klab.interpreter.lexer.utils;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class KeywordMatcher {
    private static final Map<String, TokenClass> KEYWORD_MAP = new HashMap<String, TokenClass>() {{
        put("if", TokenClass.IF_KEYWORD);
        put("endif", TokenClass.ENDIF_KEYWORD);
        put("else", TokenClass.ELSE_KEYWORD);
        put("elseif", TokenClass.ELSEIF_KEYWORD);
        put("for", TokenClass.FOR_KEYWORD);
        put("endfor", TokenClass.ENDFOR_KEYWORD);
        put("break", TokenClass.BREAK_KEYWORD);
        put("continue", TokenClass.CONTINUE_KEYWORD);
        put("function", TokenClass.FUNCTION_KEYWROD);
        put("end", TokenClass.END_KEYWORD);
    }};

    public static void changeIfKeyword(Token token) {
        TokenClass tokenClass = KEYWORD_MAP.get(token.getLexeme());
        if (Objects.nonNull(tokenClass)) {
            updateToken(token, tokenClass);
        }
    }

    private static void updateToken(Token token, TokenClass tokenClass) {
        token.setTokenClass(tokenClass);
        token.setIsKeyword(true);
    }
}
