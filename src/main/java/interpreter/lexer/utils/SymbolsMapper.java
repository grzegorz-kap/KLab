package interpreter.lexer.utils;

import interpreter.lexer.model.TokenClass;

import java.util.HashMap;
import java.util.Map;

public class SymbolsMapper {

    private static Map<String, TokenClass> TOKEN_CLASS_MAP = new HashMap<>();

    static {
        TOKEN_CLASS_MAP.put(",", TokenClass.COMMA);
        TOKEN_CLASS_MAP.put(":", TokenClass.COLON);
        TOKEN_CLASS_MAP.put(";", TokenClass.SEMICOLON);
        TOKEN_CLASS_MAP.put("(", TokenClass.OPEN_PARENTHESIS);
        TOKEN_CLASS_MAP.put(")", TokenClass.CLOSE_PARENTHESIS);
        TOKEN_CLASS_MAP.put("[", TokenClass.OPEN_BRACKET);
        TOKEN_CLASS_MAP.put("]", TokenClass.CLOSE_BRACKET);
    }

    public static TokenClass getTokenClass(String symbol) {
        return TOKEN_CLASS_MAP.get(symbol);
    }
}
