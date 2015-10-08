package interpreter.parsing.handlers

import interpreter.lexer.model.Token
import interpreter.parsing.handlers.helpers.ExpressionHelper
import interpreter.parsing.handlers.helpers.StackHelper
import interpreter.parsing.handlers.matrix.MatrixNewRowHandler
import interpreter.parsing.model.ParseClass
import interpreter.parsing.model.ParseToken
import interpreter.parsing.model.expression.ExpressionNode
import interpreter.parsing.service.ParseContextManager
import spock.lang.Specification

import java.util.function.Predicate

class MatrixNewRowHandlerTest extends Specification {

    def handler = new MatrixNewRowHandler();
    def stackHelper = Mock(StackHelper)
    def expressionHelper = Mock(ExpressionHelper)
    def parseContextManager = Mock(ParseContextManager)

    def setup() {
        handler.setStackHelper(stackHelper)
        handler.setExpressionHelper(expressionHelper)
        handler.setContextManager(parseContextManager)
    }

    def "Testing handle method"() {
        given:
        def expressions = []
        def token = Stub(Token)
        when:
        handler.handle()
        then:
        1 * stackHelper.stackToExpressionUntilTokenClass(parseContextManager, ParseClass.MATRIX_START)
        1 * expressionHelper.popUntilParseClass(parseContextManager, { predicate ->
            ParseClass.values().each {
                parseClass -> predicate.test(parseClass) == (parseClass == ParseClass.MATRIX_VERSE || parseClass == ParseClass.MATRIX_START)
            }
        } as Predicate<ParseClass>) >> expressions
        1 * parseContextManager.tokenAt(0) >> token
        1 * parseContextManager.addExpression({
            it.value.parseClass == ParseClass.MATRIX_VERSE
            it.value.token == token
        } as ExpressionNode<ParseToken>)
        1 * parseContextManager.incrementTokenPosition(1)
    }
}
