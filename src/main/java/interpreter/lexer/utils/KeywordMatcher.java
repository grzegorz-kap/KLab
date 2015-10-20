package interpreter.lexer.utils;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class KeywordMatcher {

    private Map<String, TokenClass> keywordMap = new HashMap<>();

    public KeywordMatcher() {
        keywordMap.put("if", TokenClass.IF_KEYWORD);
        keywordMap.put("endif", TokenClass.ENDIF_KEYWORD);
        keywordMap.put("else", TokenClass.ELSE_KEYWORD);
    }

    public void changeIfKeyword(Token token) {
        TokenClass tokenClass = keywordMap.get(token.getLexeme());
        if (Objects.nonNull(tokenClass)) {
            updateToken(token, tokenClass);
        }
    }

    private void updateToken(Token token, TokenClass tokenClass) {
        token.setTokenClass(tokenClass);
        token.setIsKeyword(true);
    }
}
