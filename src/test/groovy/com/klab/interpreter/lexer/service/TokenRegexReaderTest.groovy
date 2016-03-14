package com.klab.interpreter.lexer.service

import com.klab.interpreter.lexer.model.TokenClass
import com.klab.interpreter.lexer.model.TokenizerContext
import com.klab.interpreter.lexer.utils.TokenRegexReader
import spock.lang.Specification

import java.util.regex.Pattern

class TokenRegexReaderTest extends Specification {

    def tokenReader = new TokenRegexReader()

    def setup() {
        tokenReader.setTokenizerContext(new TokenizerContext("123.321"))
    }

    def "Testing reading token for pattern"() {
        given: "Pattern and expected token class"
        def pattern = Pattern.compile("\\d+\\.?\\d*");
        def tokenClass = TokenClass.NUMBER;
        when: "Trying to read token"
        def result = tokenReader.readToken(pattern, tokenClass);
        then: "Read method return correct token"
        result.lexeme == "123.321"
        result.tokenClass == tokenClass
    }
}
