package interpreter.translate.handlers

import interpreter.parsing.model.ParseClass
import interpreter.parsing.model.expression.Expression
import interpreter.translate.model.instruction.Instruction
import interpreter.translate.model.instruction.InstructionCode
import interpreter.translate.service.TranslateContextManager
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
        1 * handler.translateContextManager.addInstruction({
            it.instructionCode == InstructionCode.MATRIX
            it.argumentsNumber == 10
        } as Instruction)
    }
}
