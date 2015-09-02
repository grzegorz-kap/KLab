package interpreter.lexer.helper

import interpreter.lexer.model.TokenizerContext
import spock.lang.Specification

class TokenStartMatcherTest extends Specification {

    private TokenStartMatcher tokenStartMatcher;
    private TokenizerContext tokenizerContext = Mock(TokenizerContext)

    def setup() {
        tokenStartMatcher = new TokenStartMatcher(tokenizerContext)
    }

    def "Testing number start detection"() {
        when:
            def result = tokenStartMatcher.isNumberStart();
        then:
            1 * tokenizerContext.charAt(0) >> '0'
            result == true
    }

    def "Testing number which begins with dot"() {
        when:
            def result = tokenStartMatcher.isNumberStart();
        then:
            1 * tokenizerContext.charAt(0) >> '.'
            1 * tokenizerContext.isCharAt(0, '.') >> true
            1 * tokenizerContext.charAt(1) >> '2'
            result == true

        when:
            result = tokenStartMatcher.isNumberStart();
        then:
            1 * tokenizerContext.isCharAt(0, '.') >> true;
            1 * tokenizerContext.charAt(1) >> '.'
            result == false;
    }

}
