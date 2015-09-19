package interpreter.lexer.helper;

import interpreter.lexer.model.TokenizerContext;

public class TokenStartMatcher {

    private TokenizerContext tokenizerContext;

    public TokenStartMatcher(TokenizerContext tokenizerContext) {
        this.tokenizerContext = tokenizerContext;
    }

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
}
