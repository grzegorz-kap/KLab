package interpreter.translate.handlers

import interpreter.parsing.model.ParseToken
import interpreter.parsing.model.expression.ExpressionValue
import interpreter.translate.factory.OperatorInstructionCodesFactory
import interpreter.translate.model.Instruction
import interpreter.translate.model.InstructionCode
import interpreter.translate.service.TranslateContextManager
import spock.lang.Specification

import static com.natpryce.makeiteasy.MakeItEasy.a
import static com.natpryce.makeiteasy.MakeItEasy.make
import static interpreter.parsing.makers.OperatorTokenMaker.saveOperatorToken

class OperatorTranslateHandlerTest extends Specification {

    def operatorTranslateHandler = new OperatorTranslateHandler();

    def setup() {
        operatorTranslateHandler.operatorInstructionCodesFactory = Mock(OperatorInstructionCodesFactory)
        operatorTranslateHandler.translateContextManager = Mock(TranslateContextManager)
    }

    def "Testing operator translate handle method"() {
        given:
        def operator = make a(saveOperatorToken)
        def expressionValue = new ExpressionValue<ParseToken>();
        expressionValue.setValue(operator)

        when:
        operatorTranslateHandler.handle(expressionValue)

        then:
        1 * operatorTranslateHandler.operatorInstructionCodesFactory.get(operator.operatorCode) >> InstructionCode.ADD
        1 * operatorTranslateHandler.tCM.addInstruction({
            it.instructionCode == InstructionCode.ADD
            it.argumentsNumber == 0
            it.objectDataList.size() == 0
        } as Instruction)

    }
}
