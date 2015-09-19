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
        result
    }

    def "Testing number which begins with dot"() {
        when:
        def result = tokenStartMatcher.isNumberStart();
        then:
        1 * tokenizerContext.charAt(0) >> '.'
        1 * tokenizerContext.isCharAt(0, '.') >> true
        1 * tokenizerContext.charAt(1) >> '2'
        result

        when:
        result = tokenStartMatcher.isNumberStart();
        then:
        1 * tokenizerContext.isCharAt(0, '.') >> true;
        1 * tokenizerContext.charAt(1) >> '.'
        !result
    }

    def "Testing is number start"() {
        when:
        def result = tokenStartMatcher.isWordStart()
        then:
        1 * tokenizerContext.charAt(0) >> 'f'
        result

        when:
        result = tokenStartMatcher.isWordStart()
        then:
        1 * tokenizerContext.isCharAt(0, '_') >> true
        result

        when:
        result = tokenStartMatcher.isWordStart()
        then:
        1 * tokenizerContext.isCharAt(0, '$') >> true
        result

        when:
        result = tokenStartMatcher.isWordStart()
        then:
        1 * tokenizerContext.charAt(0) >> 3
        !result
    }

    def "Testing isNewLineStart"() {
        when:
        def result = tokenStartMatcher.isNewLineStart()
        then:
        1 * tokenizerContext.isCharAt(0, '\n') >> true
        result

        when:
        result = tokenStartMatcher.isNewLineStart()
        then:
        1 * tokenizerContext.isCharAt(0, '\n') >> false
        !result
    }

    def "Testing isSpaceOrTabulatorStart"() {
        when:
        def result = tokenStartMatcher.isSpaceOrTabulatorStart()
        then:
        1 * tokenizerContext.isCharAt(0, ' ') >> true
        result

        when:
        result = tokenStartMatcher.isSpaceOrTabulatorStart()
        then:
        1 * tokenizerContext.isCharAt(0, '\t') >> true
        result

        when:
        result = tokenStartMatcher.isSpaceOrTabulatorStart()
        then:
        2 * tokenizerContext.isCharAt(0, _) >> false
        !result
    }

}
