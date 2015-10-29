package interpreter.lexer.utils;

import interpreter.lexer.model.TokenizerContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TokenStartMatcher {

    private TokenizerContext tokenizerContext;

    public boolean isNumberStart() {
        return Character.isDigit(tokenizerContext.charAt(0)) ||
                tokenizerContext.isCharAt(0, '.') && Character.isDigit(tokenizerContext.charAt(1));
    }

    public boolean isWordStart() {
        return Character.isLetter(tokenizerContext.charAt(0))
                || tokenizerContext.isCharAt(0, '_')
                || tokenizerContext.isCharAt(0, '$');
    }

    public boolean isSpaceOrTabulatorStart() {
        return tokenizerContext.isCharAt(0, ' ') || tokenizerContext.isCharAt(0, '\t');
    }

    public boolean isNewLineStart() {
        return tokenizerContext.isCharAt(0, '\n');
    }

    public void setTokenizerContext(TokenizerContext tokenizerContext) {
        this.tokenizerContext = tokenizerContext;
    }
}
