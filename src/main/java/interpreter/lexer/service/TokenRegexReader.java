package interpreter.lexer.service;

import interpreter.lexer.exception.TokenReadException;
import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenizerContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenRegexReader {

    private TokenizerContext tokenizerContext;

    public TokenRegexReader(TokenizerContext tokenizerContext) {
        this.tokenizerContext = tokenizerContext;
    }

    public Token readToken(final Pattern pattern, TokenClass tokenClass) {
        Token token = new Token();
        token.setLexeme(tryMatchLexeme(pattern));
        token.setTokenClass(tokenClass);
        return token;
    }

    private String tryMatchLexeme(final Pattern pattern) {
        Matcher matcher = pattern.matcher(tokenizerContext.getInputText());
        if (!matcher.find(tokenizerContext.getIndex())) {
            throw new TokenReadException("Cannot read token", tokenizerContext);
        }
        return matcher.group();
    }
}
