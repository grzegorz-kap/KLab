package interpreter.parsing.handlers

import interpreter.lexer.model.TokenClass
import interpreter.parsing.model.NumericType
import interpreter.parsing.model.tokens.NumberToken
import interpreter.parsing.service.ParseContextManager
import spock.lang.Specification

import static com.natpryce.makeiteasy.MakeItEasy.*
import static interpreter.lexer.makers.TokenMaker.*

class NumberHandlerTest extends Specification {

    def NumberHandler numberHandler = new NumberHandler();

    def "Testing handle method for numbers"() {
        given:
        def token = make a(saveToken, with(lexame, "3232.2121"), with(tokenClass, TokenClass.NUMBER))
        def result = null;
        numberHandler.contextManager = Mock(ParseContextManager)
        when:
        numberHandler.handle()
        then:
        1 * numberHandler.contextManager.tokenAt(0) >> token
        1 * numberHandler.contextManager.incrementTokenPosition(1)
        1 * numberHandler.contextManager.addExpressionValue(_ as NumberToken<Double>) >> { result = it[0] }
        result.token == token
        result.getNumericType() == NumericType.DOUBLE
    }
}

