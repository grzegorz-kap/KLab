package interpreter.parsing.service.handlers.matrix

import interpreter.lexer.model.TokenClass
import interpreter.parsing.model.ParseClass
import interpreter.parsing.model.ParseToken
import interpreter.parsing.model.expression.ExpressionNode
import interpreter.parsing.service.ParseContextManager
import interpreter.parsing.service.handlers.MatrixEndParseHandler
import interpreter.parsing.service.handlers.helpers.ExpressionHelper
import interpreter.parsing.service.handlers.helpers.StackHelper
import spock.lang.Specification

class MatrixEndParseHandlerTest extends Specification {

    def handler = new MatrixEndParseHandler()
    def parseContextManager = Mock(ParseContextManager)
    def stackHelper = Mock(StackHelper)
    def expressionHelper = Mock(ExpressionHelper)

    def setup() {
        handler.setContextManager(parseContextManager)
        handler.setStackHelper(stackHelper)
        handler.setExpressionHelper(expressionHelper)
    }

    def "Test supported class"() {
        when:
        def result = handler.getSupportedTokenClass()

        then:
        result == TokenClass.CLOSE_BRACKET
    }

    def "Test handle method"() {
        given:
        def expressions = []
        def parseToken = new ParseToken()
        def matrixNode = new ExpressionNode<ParseToken>();
        matrixNode.value = parseToken

        when:
        handler.handle()

        then:
        1 * stackHelper.stackToExpressionUntilTokenClass(parseContextManager, ParseClass.MATRIX_START) >> true
        1 * expressionHelper.popUntilParseClass(parseContextManager, { predicate ->
            ParseClass.values().each { parseClass -> predicate.test(parseClass) == parseClass.equals(ParseClass.MATRIX_START) }
        }) >> expressions
        1 * parseContextManager.popExpression() >> matrixNode
        1 * parseContextManager.addExpression({ matrixNode = it } as ExpressionNode<ParseToken>)
        1 * parseContextManager.incrementTokenPosition(1)
        matrixNode.children == expressions
        parseToken.parseClass == ParseClass.MATRIX
    }
}
