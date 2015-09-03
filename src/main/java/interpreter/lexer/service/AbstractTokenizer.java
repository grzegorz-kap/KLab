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

    public abstract boolean tryReadOperator();

    public abstract boolean tryReadOtherSymbol();

    public TokenList readTokens(String inputText) {
        setContext(inputText);
        tokenStartMatcher = new TokenStartMatcher(tokenizerContext);
        tokenizerContextManager = new TokenizerContextManager(tokenizerContext);
        tokenReader = new TokenRegexReader(tokenizerContext);
        process();
        return tokenizerContext.getTokenList();
    }

    protected void setContext(String inputText) {
        tokenizerContext = new TokenizerContext(inputText);
    }

    private void process() {
        while (!tokenizerContext.isInputEnd()) {
            takeAction();
        }
    }

    private void takeAction() {
        if (tokenStartMatcher.isNumberStart()) {
            onNumber();
            return;
        }
        if (tokenStartMatcher.isWordStart()) {
            onWord();
            return;
        }
        if (tokenStartMatcher.isSpaceOrTabulatorStart()) {
            onSpaceOrTabulator();
            return;
        }
        if(tryReadOperator()){
            return;
        }
        if (tokenStartMatcher.isNewLineStart()) {
            onNewLine();
            return;
        }
        if(tryReadOtherSymbol()){
            return;
        }
        tokenizerContext.setIndex(tokenizerContext.getIndex()+1);
    }
}
