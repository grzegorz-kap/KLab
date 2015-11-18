package interpreter.lexer.service;

import interpreter.lexer.exception.UnrecognizedTokenException;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.model.TokenizerContext;
import interpreter.lexer.utils.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTokenizer implements Tokenizer {

    public static final String UNRECOGNIZED_TOKEN_MESSAGE = "Unrecognized token.";

    protected TokenizerContext tC;
    protected TokenizerContextManager tCM;
    protected TokenRegexReader tokenRegexReader;
    protected TokenMatcher tokenMatcher;
    protected SymbolsMapper symbolsMapper;
    protected KeywordMatcher keywordMatcher;
    private TokenStartMatcher tokenStartMatcher;


    public TokenList readTokens(String inputText) {
        setContext(inputText);
        setState();
        process();
        return tC.getTokenList();
    }

    public abstract void onNumber();

    public abstract void onWord();

    public abstract void onSpaceOrTabulator();

    public abstract void onNewLine();

    public abstract boolean tryReadOperator();

    public abstract boolean tryReadOtherSymbol();

    private void setState() {
        tokenStartMatcher.setTokenizerContext(tC);
        tCM.setTokenizerContext(tC);
        tokenRegexReader.setTokenizerContext(tC);
    }

    private void process() {
        while (!tC.isInputEnd()) {
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
        throw new UnrecognizedTokenException(UNRECOGNIZED_TOKEN_MESSAGE, tC);
    }

    protected void setContext(String inputText) {
        tC = new TokenizerContext(inputText);
    }

    @Autowired
    public void settCM(TokenizerContextManager tCM) {
        this.tCM = tCM;
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
