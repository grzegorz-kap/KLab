package interpreter.lexer.utils;

import interpreter.lexer.model.TokenClass;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SymbolsMapper {

    private Map<String, TokenClass> TOKEN_CLASS_MAP = new HashMap<>();

    public SymbolsMapper() {
        TOKEN_CLASS_MAP.put(",", TokenClass.COMMA);
        TOKEN_CLASS_MAP.put(":", TokenClass.COLON);
        TOKEN_CLASS_MAP.put(";", TokenClass.SEMICOLON);
        TOKEN_CLASS_MAP.put("(", TokenClass.OPEN_PARENTHESIS);
        TOKEN_CLASS_MAP.put(")", TokenClass.CLOSE_PARENTHESIS);
        TOKEN_CLASS_MAP.put("[", TokenClass.OPEN_BRACKET);
        TOKEN_CLASS_MAP.put("]", TokenClass.CLOSE_BRACKET);
    }

    public TokenClass getTokenClass(String symbol) {
        return TOKEN_CLASS_MAP.get(symbol);
    }
}
