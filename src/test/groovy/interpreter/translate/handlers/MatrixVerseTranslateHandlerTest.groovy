package interpreter.translate.handlers

import interpreter.parsing.model.ParseClass
import interpreter.parsing.model.expression.Expression
import interpreter.translate.model.Instruction
import interpreter.translate.model.InstructionCode
import interpreter.translate.service.TranslateContextManager
import spock.lang.Specification

class MatrixVerseTranslateHandlerTest extends Specification {

    def handler = new MatrixVerseTranslateHandler()
    def translateContextManager = Mock(TranslateContextManager)

    def setup() {
        handler.setTranslateContextManager(translateContextManager)
    }

    def "Test supported class"() {
        when:
        def result = handler.getSupportedParseClass()
        then:
        result == ParseClass.MATRIX_VERSE
    }

    def "Test handle method"() {
        given:
        def expression = Mock(Expression)

        when:
        handler.handle(expression)

        then:
        1 * expression.getChildrenCount() >> 10
        1 * translateContextManager.addInstruction({
            it.instructionCode == InstructionCode.MATRIX_VERSE
            it.argumentsNumber == 10
        } as Instruction)
    }
}
