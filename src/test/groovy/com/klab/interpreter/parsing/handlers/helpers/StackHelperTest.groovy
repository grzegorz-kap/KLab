package com.klab.interpreter.parsing.handlers.helpers

import com.klab.interpreter.parsing.handlers.ParseHandler
import com.klab.interpreter.parsing.service.ParseContextManager
import spock.lang.Specification

import static com.klab.interpreter.parsing.model.ParseClass.MATRIX_START
import static com.klab.interpreter.parsing.model.ParseClass.NUMBER

class StackHelperTest extends Specification {

    def stackHelper = new StackHelper();

    def "Testing stack to expression method"() {
        given:
        def contextManager = Mock(ParseContextManager)
        def stackHandler = Stub(ParseHandler)
        def count = 3
        stackHandler.handleStackFinish() >> { count--; }

        when:
        def result = stackHelper.stackToExpressionUntilParseClass(contextManager, MATRIX_START)

        then:
        4 * contextManager.isStackEmpty() >> { return count == 0; }
        3 * contextManager.stackPeekClass() >> { return count == 1 ? MATRIX_START : NUMBER }
        2 * contextManager.getParseHandler(_) >> { return stackHandler };
        result
    }

    def "Stack to expression when empty stack"() {
        given:
        def contextManager = Stub(ParseContextManager)
        contextManager.isStackEmpty() >> true

        when:
        def result = stackHelper.stackToExpressionUntilParseClass(contextManager, MATRIX_START)

        then:
        !result
    }
}
