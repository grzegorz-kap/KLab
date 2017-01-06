package com.klab.interpreter.lexer.service;

import com.klab.interpreter.lexer.exception.UnrecognizedTokenException;
import com.klab.interpreter.lexer.model.TokenList;
import com.klab.interpreter.lexer.model.TokenizerContext;
import com.klab.interpreter.lexer.utils.TokenRegexReader;
import com.klab.interpreter.lexer.utils.TokenStartMatcher;

public abstract class AbstractTokenizerService implements TokenizerService {
    private static final String UNRECOGNIZED_TOKEN_MESSAGE = "Unrecognized token.";

    TokenizerContext tokenizerContext;
    TokenizerContextManager tokenizerContextManager;
    TokenRegexReader tokenRegexReader;

    private TokenStartMatcher tokenStartMatcher;

    public synchronized TokenList readTokens(String inputText) {
        setContext(inputText);
        setState();
        process();
        return tokenizerContext.getTokenList();
    }

    public abstract void onNumber();

    public abstract void onWord();

    public abstract void onSpaceOrTabulator();

    public abstract void onNewLine();

    public abstract boolean tryReadOperator();

    public abstract boolean tryReadOtherSymbol();

    public abstract boolean tryReadString();

    private void setState() {
        tokenStartMatcher = new TokenStartMatcher(tokenizerContext);
        tokenizerContextManager = new TokenizerContextManager(tokenizerContext);
        tokenRegexReader = new TokenRegexReader(tokenizerContext);
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
        if (tryReadString()) {
            return;
        }
        if (tryReadOperator()) {
            return;
        }
        if (tokenStartMatcher.isNewLineStart()) {
            onNewLine();
            return;
        }
        if (tryReadOtherSymbol()) {
            return;
        }
        throw new UnrecognizedTokenException(UNRECOGNIZED_TOKEN_MESSAGE, tokenizerContext);
    }

    protected void setContext(String inputText) {
        tokenizerContext = new TokenizerContext(inputText);
    }
}
