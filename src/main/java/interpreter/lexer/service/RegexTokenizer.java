package interpreter.lexer.service;

import interpreter.lexer.model.RegexTokenizerContext;
import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.lexer.utils.SymbolsMapper;
import interpreter.lexer.utils.TokenMatcher;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTokenizer extends AbstractTokenizer {

    public static final String NUMBER_PATTERN = "^((\\d+\\.?\\d*)|(\\.\\d+))([eE][-+]?\\d+)?i?";
    public static final String WORD_PATTERN = "^[A-Za-z_\\$][A-Za-z_\\$0-9]*";
    public static final String SPACE_PATTERN = "^[ \\t]+";
    public static final String NEWLINE_PATTERN = "^[\\n\\t ]+";

    public static final Pattern NUMBER_REGEX = Pattern.compile(NUMBER_PATTERN);
    public static final Pattern WORD_REGEX = Pattern.compile(WORD_PATTERN);
    public static final Pattern SPACE_REGEX = Pattern.compile(SPACE_PATTERN);
    public static final Pattern NEWLINE_REGEX = Pattern.compile(NEWLINE_PATTERN);

    @Override
    public void onNumber() {
        addToken(NUMBER_REGEX, TokenClass.NUMBER);
    }

    @Override
    public void onWord() {
        addToken(WORD_REGEX, TokenClass.WORD);
    }

    @Override
    public void onSpaceOrTabulator() {
        addToken(SPACE_REGEX, TokenClass.SPACE);
    }

    @Override
    public void onNewLine() {
        addToken(NEWLINE_REGEX, TokenClass.NEW_LINE);
    }

    @Override
    public boolean tryReadOperator() {
        String result = tryRead(TokenMatcher.getOperatorRegex());
        if(Objects.nonNull(result)) {
            addToken(result, TokenClass.OPERATOR);
        }
        return Objects.nonNull(result);
    }

    @Override
    public boolean tryReadOtherSymbol() {
        String result = tryRead(TokenMatcher.getSymbolsRegex());
        if(Objects.nonNull(result)){
            addToken(result, SymbolsMapper.getTokenClass(result));
        }
        return Objects.nonNull(result);
    }

    @Override
    protected void setContext(String inputText) {
        tokenizerContext = new RegexTokenizerContext(inputText);
    }

    private String tryRead(Pattern pattern) {
        Matcher matcher = pattern.matcher(tokenizerContext.getInputText());
        return matcher.find() ? matcher.group() : null;
    }

    private void addToken(final Pattern pattern, TokenClass tokenClass) {
        tokenizerContextManager.addToken(tokenReader.readToken(pattern, tokenClass));
    }

    private void addToken(final String lexame, TokenClass tokenClass) {
        Token token = new Token();
        token.setLexeme(lexame);
        token.setTokenClass(tokenClass);
        tokenizerContextManager.addToken(token);
    }
}
