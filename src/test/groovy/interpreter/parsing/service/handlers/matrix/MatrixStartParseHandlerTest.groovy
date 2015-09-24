package interpreter.parsing.service.handlers.matrix

import interpreter.lexer.model.TokenClass
import interpreter.parsing.model.ParseClass
import interpreter.parsing.model.tokens.MatrixStartToken
import interpreter.parsing.service.ParseContextManager
import spock.lang.Specification

import static com.natpryce.makeiteasy.MakeItEasy.*
import static interpreter.lexer.makers.TokenMaker.saveToken
import static interpreter.lexer.makers.TokenMaker.tokenClass

class MatrixStartParseHandlerTest extends Specification {

    def matrixHandler = new MatrixStartParseHandler()
    def parseContextManager = Mock(ParseContextManager)

    def setup() {
        matrixHandler.setContextManager(parseContextManager)
    }

    def "Test supported class"() {
        when:
        def result = matrixHandler.getSupportedTokenClass()

        then:
        result == TokenClass.OPEN_BRACKET
    }

    def "Test handle method"() {
        given:
        def token = make a(saveToken, with(tokenClass, TokenClass.OPEN_BRACKET))
        def parseTokenStack = new MatrixStartToken()
        def parseTokenExpression = new MatrixStartToken()

        when:
        matrixHandler.handle()

        then:
        1 * parseContextManager.tokenAt(0) >> token
        1 * parseContextManager.incrementTokenPosition(1)
        1 * parseContextManager.addExpressionNode({ parseTokenExpression = it } as MatrixStartToken)
        1 * parseContextManager.stackPush({ parseTokenStack = it } as MatrixStartToken)
        parseTokenStack == parseTokenExpression
        parseTokenStack.token == token
        parseTokenStack.parseClass == ParseClass.MATRIX_START

    }
}
