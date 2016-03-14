package com.klab.interpreter.translate.handlers

import com.klab.interpreter.parsing.model.ParseClass
import com.klab.interpreter.parsing.model.expression.Expression
import com.klab.interpreter.translate.model.Instruction
import com.klab.interpreter.translate.model.InstructionCode
import com.klab.interpreter.translate.service.TranslateContextManager
import spock.lang.Specification

class MatrixTranslateHandlerTest extends Specification {

    def handler = new MatrixTranslateHandler();

    def "Test supported ParseClass"() {
        when:
        def result = handler.getSupportedParseClass()
        then:
        result == ParseClass.MATRIX
    }

    def "Test handle method"() {
        given:
        def expression = Mock(Expression)
        handler.translateContextManager = Mock(TranslateContextManager)
        when:
        handler.handle(expression)
        then:
        1 * expression.getChildrenCount() >> 10
        1 * handler.tCM.addInstruction({
            it.instructionCode == InstructionCode.MATRIX
            it.argumentsNumber == 10
        } as Instruction)
    }
}
