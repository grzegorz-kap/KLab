package interpreter.lexer.utils;

import interpreter.lexer.exception.TokenReadException;
import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenizerContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TokenRegexReader {

    private TokenizerContext tokenizerContext;

    public Token readToken(final Pattern pattern, TokenClass tokenClass) {
        Token token = new Token();
        token.setLexeme(tryMatchLexeme(pattern));
        token.setTokenClass(tokenClass);
        return token;
    }

    public String tryMatchLexeme(final Pattern pattern) {
        Matcher matcher = pattern.matcher(tokenizerContext.getInputText());
        if (!matcher.find()) {
            throw new TokenReadException("Cannot read token", tokenizerContext);
        }
        return matcher.group();
    }

    public void setTokenizerContext(TokenizerContext tokenizerContext) {
        this.tokenizerContext = tokenizerContext;
    }
}
