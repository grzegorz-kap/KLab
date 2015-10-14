package interpreter.lexer.service;

import interpreter.lexer.exception.UnrecognizedTokenException;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.model.TokenizerContext;
import interpreter.lexer.utils.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTokenizer implements Tokenizer {

    public static final String UNRECOGNIZED_TOKEN_MESSAGE = "Unrecognized token.";

    protected TokenizerContext tokenizerContext;
    protected TokenizerContextManager tokenizerContextManager;
    protected TokenRegexReader tokenRegexReader;
    protected TokenMatcher tokenMatcher;
    protected SymbolsMapper symbolsMapper;
    protected KeywordMatcher keywordMatcher;
    private TokenStartMatcher tokenStartMatcher;


    public TokenList readTokens(String inputText) {
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

    private void setState() {
        tokenStartMatcher.setTokenizerContext(tokenizerContext);
        tokenizerContextManager.setTokenizerContext(tokenizerContext);
        tokenRegexReader.setTokenizerContext(tokenizerContext);
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

    @Autowired
    public void setTokenizerContextManager(TokenizerContextManager tokenizerContextManager) {
        this.tokenizerContextManager = tokenizerContextManager;
    }

    @Autowired
    public void setTokenStartMatcher(TokenStartMatcher tokenStartMatcher) {
        this.tokenStartMatcher = tokenStartMatcher;
    }

    @Autowired
    public void setTokenRegexReader(TokenRegexReader tokenRegexReader) {
        this.tokenRegexReader = tokenRegexReader;
    }

    @Autowired
    public void setTokenMatcher(TokenMatcher tokenMatcher) {
        this.tokenMatcher = tokenMatcher;
    }

    @Autowired
    public void setSymbolsMapper(SymbolsMapper symbolsMapper) {
        this.symbolsMapper = symbolsMapper;
    }

    @Autowired
    public void setKeywordMatcher(KeywordMatcher keywordMatcher) {
        this.keywordMatcher = keywordMatcher;
    }
}
