package interpreter.parsing.service.handlers.matrix

import interpreter.lexer.model.TokenClass
import interpreter.parsing.model.ParseClass
import interpreter.parsing.service.ParseContextManager
import interpreter.parsing.service.handlers.MatrixNewColumnParseHandler
import interpreter.parsing.service.handlers.helpers.StackHelper
import spock.lang.Specification

class MatrixNewColumnParseHandlerTest extends Specification {

    def parseHandler = new MatrixNewColumnParseHandler();

    def "Return correct supported TokenClass"() {
        when:
        def supported = parseHandler.getSupportedTokenClass()
        then:
        supported == TokenClass.COMMA
    }

    def "Testing handle method"() {
        given:
        parseHandler.parseContextManager = Mock(ParseContextManager)
        parseHandler.stackHelper = Mock(StackHelper)

        when:
        parseHandler.handle()

        then:
        1 * parseHandler.stackHelper.stackToExpressionUntilTokenClass(parseHandler.parseContextManager, ParseClass.MATRIX_START) >> 1
        1 * parseHandler.parseContextManager.incrementTokenPosition(1)
    }
}
