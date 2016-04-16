package com.klab.interpreter.translate.handlers

import com.klab.interpreter.parsing.model.ParseToken
import com.klab.interpreter.parsing.model.expression.ExpressionValue
import com.klab.interpreter.translate.factory.OperatorInstructionCodesFactory
import com.klab.interpreter.translate.model.Instruction
import com.klab.interpreter.translate.model.InstructionCode
import com.klab.interpreter.translate.service.TranslateContextManager
import spock.lang.Specification

import static com.klab.interpreter.parsing.makers.OperatorTokenMaker.saveOperatorToken
import static com.natpryce.makeiteasy.MakeItEasy.a
import static com.natpryce.makeiteasy.MakeItEasy.make

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
