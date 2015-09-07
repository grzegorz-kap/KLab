package interpreter.lexer.service

import interpreter.lexer.model.TokenClass
import interpreter.lexer.model.TokenizerContext
import spock.lang.Specification

import java.util.regex.Pattern

class TokenRegexReaderTest extends Specification {

    def tokenReader = new TokenRegexReader(new TokenizerContext("123.321"));

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
