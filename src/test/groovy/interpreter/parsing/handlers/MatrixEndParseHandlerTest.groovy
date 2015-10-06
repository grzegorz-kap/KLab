package interpreter.parsing.handlers

import interpreter.lexer.model.TokenClass
import interpreter.parsing.handlers.helpers.ExpressionHelper
import interpreter.parsing.handlers.helpers.StackHelper
import interpreter.parsing.handlers.matrix.MatrixEndParseHandler
import interpreter.parsing.handlers.matrix.MatrixNewRowHandler
import interpreter.parsing.model.BalanceType
import interpreter.parsing.model.ParseClass
import interpreter.parsing.model.ParseToken
import interpreter.parsing.model.expression.ExpressionNode
import interpreter.parsing.service.BalanceContextService
import interpreter.parsing.service.ParseContextManager
import spock.lang.Specification

class MatrixEndParseHandlerTest extends Specification {

    def handler = new MatrixEndParseHandler()
    def parseContextManager = Mock(ParseContextManager)
    def stackHelper = Mock(StackHelper)
    def expressionHelper = Mock(ExpressionHelper)
    def balanceContextService = Mock(BalanceContextService)
    def rowHandler = Mock(MatrixNewRowHandler)

    def setup() {
        handler.setMatrixNewRowHandler(rowHandler)
        handler.setContextManager(parseContextManager)
        handler.setStackHelper(stackHelper)
        handler.setExpressionHelper(expressionHelper)
        handler.setBalanceContextService(balanceContextService)
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
        parseToken.setParseClass(ParseClass.NUMBER)
        def matrixNode = new ExpressionNode<ParseToken>();
        matrixNode.value = parseToken

        when:
        handler.handle()

        then:
        1 * stackHelper.stackToExpressionUntilTokenClass(parseContextManager, ParseClass.MATRIX_START) >> true
        1 * expressionHelper.popUntilParseClass(parseContextManager, { predicate ->
            ParseClass.values().each { parseClass -> predicate.test(parseClass) == parseClass.equals(ParseClass.MATRIX_START) }
        }) >> expressions
        1 * parseContextManager.expressionPop() >> matrixNode
        1 * parseContextManager.addExpression({ matrixNode = it } as ExpressionNode<ParseToken>)
        1 * parseContextManager.incrementTokenPosition(1)
        1 * balanceContextService.popOrThrow(parseContextManager, BalanceType.INSIDE_MATRIX)
        1 * parseContextManager.expressionSize() >> 1
        1 * parseContextManager.expressionPeek() >> new ExpressionNode<>(parseToken)
        1 * rowHandler.handleAction()
        matrixNode.children == expressions
        parseToken.parseClass == ParseClass.MATRIX
    }
}
