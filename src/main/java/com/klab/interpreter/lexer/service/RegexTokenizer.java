package com.klab.interpreter.lexer.service;

import com.klab.interpreter.lexer.model.RegexTokenizerContext;
import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.lexer.utils.KeywordMatcher;
import com.klab.interpreter.lexer.utils.SymbolsMapper;
import com.klab.interpreter.lexer.utils.TokenMatcher;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RegexTokenizer extends AbstractTokenizer {
    private static final String NUMBER_PATTERN = "^((\\d+\\.?\\d*)|(\\.\\d+))([eE][-+]?\\d+)?i?";
    private static final String WORD_PATTERN = "^[A-Za-z_\\$][A-Za-z_\\$0-9]*";
    private static final String SPACE_PATTERN = "^[ \\t]+";
    private static final String NEWLINE_PATTERN = "^[\\n\\t\\r ]+";
    private static final Pattern NUMBER_REGEX = Pattern.compile(NUMBER_PATTERN);
    private static final Pattern WORD_REGEX = Pattern.compile(WORD_PATTERN);
    private static final Pattern SPACE_REGEX = Pattern.compile(SPACE_PATTERN);
    private static final Pattern NEWLINE_REGEX = Pattern.compile(NEWLINE_PATTERN);

    @Override
    public void onNumber() {
        addToken(NUMBER_REGEX, TokenClass.NUMBER);
    }

    @Override
    public void onWord() {
        Token token = tokenRegexReader.readToken(WORD_REGEX, TokenClass.WORD);
        KeywordMatcher.changeIfKeyword(token);
        tCM.addToken(token);
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
        if (tC.isCharAt(0, ':')) {
            TokenClass prev = tCM.tokenClassAt(0);
            if (TokenClass.COMMA.equals(prev) || TokenClass.OPEN_PARENTHESIS.equals(prev)) {
                return false;
            }
        }

        String result = tryRead(TokenMatcher.getOperatorRegex());
        if (Objects.nonNull(result)) {
            addToken(result, TokenClass.OPERATOR);
        }
        return Objects.nonNull(result);
    }

    @Override
    public boolean tryReadOtherSymbol() {
        String result = tryRead(TokenMatcher.getSymbolsRegex());
        if (Objects.isNull(result)) {
            return false;
        }
        result = result.replaceAll("[ \\t]", "");

        if (!StringUtils.isEmpty(result)) {
            addToken(result, SymbolsMapper.getTokenClass(result));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void setContext(String inputText) {
        tC = new RegexTokenizerContext(inputText);
    }

    private String tryRead(Pattern pattern) {
        Matcher matcher = pattern.matcher(tC.getInputText());
        return matcher.find() ? matcher.group() : null;
    }

    private void addToken(final Pattern pattern, TokenClass tokenClass) {
        tCM.addToken(tokenRegexReader.readToken(pattern, tokenClass));
    }

    private void addToken(final String lexame, TokenClass tokenClass) {
        Token token = new Token();
        token.setLexeme(lexame);
        token.setTokenClass(tokenClass);
        tCM.addToken(token);
    }
}
