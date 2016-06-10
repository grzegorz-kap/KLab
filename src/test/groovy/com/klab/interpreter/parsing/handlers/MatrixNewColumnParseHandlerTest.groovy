package com.klab.interpreter.parsing.handlers

import com.klab.interpreter.parsing.handlers.helpers.StackHelper
import com.klab.interpreter.parsing.handlers.matrix.MatrixNewColumnParseHandler
import com.klab.interpreter.parsing.model.ParseClass
import com.klab.interpreter.parsing.service.ParseContextManager
import spock.lang.Specification

class MatrixNewColumnParseHandlerTest extends Specification {

    def parseHandler = new MatrixNewColumnParseHandler();

    def "Return correct supported TokenClass"() {
        when:
        def supported = parseHandler.getSupportedTokenClass()
        then:
        supported == null
    }

    def "Testing handle method"() {
        given:
        parseHandler.parseContextManager = Mock(ParseContextManager)
        parseHandler.stackHelper = Mock(StackHelper)

        when:
        parseHandler.handle()

        then:
        1 * parseHandler.stackHelper.stackToExpressionUntilParseClass(parseHandler.parseContextManager, ParseClass.MATRIX_START) >> 1
        1 * parseHandler.parseContextManager.incrementTokenPosition(1)
    }
}
