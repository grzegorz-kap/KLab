package com.klab.interpreter.parsing.handlers

import com.klab.interpreter.lexer.model.TokenClass
import com.klab.interpreter.parsing.model.tokens.NumberToken
import com.klab.interpreter.parsing.service.ParseContextManager
import com.klab.interpreter.types.NumericType
import spock.lang.Specification

import static com.klab.interpreter.lexer.makers.TokenMaker.*
import static com.natpryce.makeiteasy.MakeItEasy.*

class NumberHandlerTest extends Specification {

    def NumberParseHandler numberHandler = new NumberParseHandler();

    def "Testing handle method for numbers"() {
        given:
        def token = make a(saveToken, with(lexame, "3232.2121"), with(tokenClass, TokenClass.NUMBER))
        def result = null;
        numberHandler.parseContextManager = Mock(ParseContextManager)
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

