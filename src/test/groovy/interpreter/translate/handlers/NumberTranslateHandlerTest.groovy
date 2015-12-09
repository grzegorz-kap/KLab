package interpreter.translate.handlers

import interpreter.parsing.model.ParseToken
import interpreter.parsing.model.expression.ExpressionValue
import interpreter.translate.model.Instruction
import interpreter.translate.model.InstructionCode
import interpreter.translate.service.TranslateContextManager
import interpreter.types.NumericObject
import interpreter.types.scalar.NumberScalarFactory
import spock.lang.Specification

import static com.natpryce.makeiteasy.MakeItEasy.a
import static com.natpryce.makeiteasy.MakeItEasy.make
import static interpreter.parsing.makers.NumberTokenMaker.saveNumberToken

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
        1 * numberTranslateHandler.translateContextManager.addInstruction({
            it.instructionCode == InstructionCode.PUSH
            it.argumentsNumber == 1
            it.objectDataList.size() == 1
            it.objectDataList[0] == numberScalar
        } as Instruction)
    }
}
