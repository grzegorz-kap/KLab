package com.klab.interpreter.translate.handlers

import com.klab.interpreter.parsing.model.ParseToken
import com.klab.interpreter.parsing.model.expression.ExpressionValue
import com.klab.interpreter.translate.model.Instruction
import com.klab.interpreter.translate.model.InstructionCode
import com.klab.interpreter.translate.service.TranslateContextManager
import com.klab.interpreter.types.NumericObject
import com.klab.interpreter.types.scalar.NumberScalarFactory
import spock.lang.Specification

import static com.klab.interpreter.parsing.makers.NumberTokenMaker.saveNumberToken
import static com.natpryce.makeiteasy.MakeItEasy.a
import static com.natpryce.makeiteasy.MakeItEasy.make

class NumberTranslateHandlerTest extends Specification {

    def numberTranslateHandler = new NumberTranslateHandler();

    def setup() {
        numberTranslateHandler.numberScalarFactory = Mock(NumberScalarFactory)
        numberTranslateHandler.translateContextManager = Mock(TranslateContextManager)
    }

    def "Testing handle method"() {
        given:
        def numberToken = make a(saveNumberToken);
        def expressionValue = new ExpressionValue<ParseToken>();
        def numberScalar = Stub(NumericObject)
        expressionValue.setValue(numberToken);

        when:
        numberTranslateHandler.handle(expressionValue)

        then:
        1 * numberTranslateHandler.numberScalarFactory.getDouble(Double.valueOf(numberToken.token.lexeme)) >> numberScalar
        1 * numberTranslateHandler.tCM.addInstruction({
            it.instructionCode == InstructionCode.PUSH
            it.argumentsNumber == 1
            it.objectDataList.size() == 1
            it.objectDataList[0] == numberScalar
        } as Instruction)
    }
}
