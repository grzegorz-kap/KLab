package interpreter.parsing.service.handlers.helpers

import interpreter.parsing.service.ParseContextManager
import interpreter.parsing.service.handlers.ParseHandler
import spock.lang.Specification

import static interpreter.lexer.model.TokenClass.NEW_LINE
import static interpreter.lexer.model.TokenClass.OPEN_BRACKET

class StackHelperTest extends Specification {

    def stackHelper = new StackHelper();

    def "Testing stack to expression method"() {
        given:
        def contextManager = Mock(ParseContextManager)
        def stackHandler = Stub(ParseHandler)
        def count = 3
        stackHandler.handleStackFinish() >> { count--; }

        when:
        def result = stackHelper.stackToExpressionUntilTokenClass(contextManager, OPEN_BRACKET)

        then:
        4 * contextManager.isStackEmpty() >> { return count == 0; }
        5 * contextManager.stackPeekClass() >> { return count == 1 ? OPEN_BRACKET : NEW_LINE }
        2 * contextManager.getParseHandler(NEW_LINE) >> { return stackHandler };
        result
    }

    def "Stack to expression when empty stack"() {
        given:
        def contextManager = Stub(ParseContextManager)
        contextManager.isStackEmpty() >> true

        when:
        def result = stackHelper.stackToExpressionUntilTokenClass(contextManager, OPEN_BRACKET)

        then:
        !result
    }
}
