package com.klab.interpreter.lexer.service;

import com.google.common.collect.Lists;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.klab.interpreter.lexer.model.TokenClass.*;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RegexTokenizerService extends AbstractTokenizerService {
    private static final Pattern NUMBER_REGEX = Pattern.compile("^((\\d+\\.?\\d*)|(\\.\\d+))([eE][-+]?\\d+)?i?");
    private static final Pattern WORD_REGEX = Pattern.compile("^[A-Za-z_$][A-Za-z_$0-9]*");
    private static final Pattern SPACE_REGEX = Pattern.compile("^[ \\t]+");
    private static final Pattern NEWLINE_REGEX = Pattern.compile("^[\\n\\t\\r ]+");

    private ArrayList<TokenClass> noStringPrecursors = Lists.newArrayList(NUMBER, CLOSE_PARENTHESIS, CLOSE_BRACKET, WORD);

    @Override
    public void onNumber() {
        addToken(NUMBER_REGEX, NUMBER);
    }

    @Override
    public void onWord() {
        Token token = tokenRegexReader.readToken(WORD_REGEX, WORD);
        KeywordMatcher.changeIfKeyword(token);
        tokenizerContextManager.addToken(token);
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
    public boolean tryReadString() {
        if (tokenizerContext.isCharAt(0, '\'') && !noStringPrecursors.contains(tokenizerContextManager.tokenClassAt(0))) {
            StringBuilder lexeme = new StringBuilder("'");
            int offset = 1;
            char character;
            do {
                character = tokenizerContext.charAt(offset++);
                if ("\n\0".indexOf(character) != -1) {
                    throw new RuntimeException("Error reading string");
                }
                lexeme.append(character);
            } while (character != '\'');
            addToken(lexeme.toString(), TokenClass.STRING);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean tryReadOperator() {
        if (tokenizerContext.isCharAt(0, ':')) {
            TokenClass prev = tokenizerContextManager.tokenClassAt(0);
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
        tokenizerContext = new RegexTokenizerContext(inputText);
    }

    private String tryRead(Pattern pattern) {
        Matcher matcher = pattern.matcher(tokenizerContext.getInputText());
        return matcher.find() ? matcher.group() : null;
    }

    private void addToken(final Pattern pattern, TokenClass tokenClass) {
        tokenizerContextManager.addToken(tokenRegexReader.readToken(pattern, tokenClass));
    }

    private void addToken(final String lexame, TokenClass tokenClass) {
        Token token = new Token();
        token.setLexeme(lexame);
        token.setTokenClass(tokenClass);
        tokenizerContextManager.addToken(token);
    }
}
