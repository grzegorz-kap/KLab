package interpreter.lexer.service

import interpreter.lexer.model.TokenClass
import interpreter.lexer.model.TokenizerContext
import spock.lang.Specification

import static com.natpryce.makeiteasy.MakeItEasy.*
import static interpreter.lexer.makers.TokenMaker.*

class TokenizerContextManagerTest extends Specification {

    private TokenizerContextManager manager;
    private TokenizerContext tokenizerContext;

    def setup() {
        tokenizerContext = Mock(TokenizerContext)
        manager = new TokenizerContextManager(tokenizerContext)
    }

    def "Testing addToken(Token) method (number)"() {
        given:
        def token = make(a(saveToken, with(column, null), with(line, null)))
        when:
        manager.addToken(token)
        then:
        1 * tokenizerContext.addToken(token) >> {}
        1 * tokenizerContext.getIndex() >> 3
        1 * tokenizerContext.setIndex(3 + token.lexeme.length())
        2 * tokenizerContext.getColumn() >> 1
        1 * tokenizerContext.setColumn(1 + token.lexeme.length())
        1 * tokenizerContext.getLine() >> 1
        token.line == 1L
        token.column == 1L
    }

    def "Testing addToken(Token) with newLine token"() {
        given:
        def token = make a(saveToken, with(lexame, "\n   \n123"), with(tokenClass, TokenClass.NEW_LINE))
        when:
        manager.addToken(token)
        then:
        1 * tokenizerContext.addToken(token) >> {}
        1 * tokenizerContext.getIndex() >> 0
        1 * tokenizerContext.setIndex(token.lexeme.length())
        1 * tokenizerContext.getColumn() >> 1
        2 * tokenizerContext.getLine() >> 1
        1 * tokenizerContext.setLine(3L)
        1 * tokenizerContext.setColumn(4L)
    }
}
