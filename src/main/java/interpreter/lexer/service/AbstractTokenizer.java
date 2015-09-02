package interpreter.lexer.service;

import interpreter.lexer.helper.TokenStartMatcher;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.model.TokenizerContext;

public abstract class AbstractTokenizer implements Tokenizer {

    protected TokenizerContext tokenizerContext;
    protected TokenizerContextManager tokenizerContextManager;
    protected TokenRegexReader tokenReader;

    private TokenStartMatcher tokenStartMatcher;

    public abstract void onNumber();

    public abstract void onWord();

    public abstract void onSpaceOrTabulator();

    public abstract void onNewLine();

    public TokenList readTokens(String inputText) {
        tokenizerContext = new TokenizerContext(inputText);
        tokenStartMatcher = new TokenStartMatcher(tokenizerContext);
        tokenizerContextManager = new TokenizerContextManager(tokenizerContext);
        tokenReader = new TokenRegexReader(tokenizerContext);
        process();
        return tokenizerContext.getTokenList();
    }

    private void process() {
        while (!tokenizerContext.isInputEnd()) {
            if (tokenStartMatcher.isNumberStart()) {
                onNumber();
            } else if (tokenStartMatcher.isWordStart()) {
                onWord();
            } else if (tokenStartMatcher.isSpaceOrTabulatorStart()) {
                onSpaceOrTabulator();
            } else if (tokenStartMatcher.isNewLineStart()) {
                onNewLine();
            } else {
                tokenizerContext.setIndex(tokenizerContext.getIndex()+1);
            }
        }
    }
}
