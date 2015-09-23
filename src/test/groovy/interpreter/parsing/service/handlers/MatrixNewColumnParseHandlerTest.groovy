package interpreter.parsing.service.handlers

import interpreter.lexer.model.TokenClass
import interpreter.parsing.service.ParseContextManager
import interpreter.parsing.service.handlers.helpers.StackHelper
import spock.lang.Specification

import static interpreter.lexer.model.TokenClass.OPEN_BRACKET


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
        1 * parseHandler.stackHelper.stackToExpressionUntilTokenClass(parseHandler.parseContextManager, OPEN_BRACKET) >> 1
        1 * parseHandler.parseContextManager.incrementTokenPosition(1)
    }


}
