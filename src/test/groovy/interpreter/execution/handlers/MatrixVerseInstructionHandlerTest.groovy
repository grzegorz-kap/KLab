package interpreter.execution.handlers

import interpreter.execution.model.ExecutionContext
import interpreter.execution.model.InstructionPointer
import interpreter.execution.service.ExecutionContextManager
import interpreter.translate.model.InstructionCode
import interpreter.types.matrix.Matrix
import interpreter.types.matrix.MatrixBuilder
import interpreter.types.matrix.MatrixFactory
import interpreter.types.scalar.DoubleScalar
import spock.lang.Specification

import static com.natpryce.makeiteasy.MakeItEasy.*
import static interpreter.translate.makers.InstructionMaker.*

class MatrixVerseInstructionHandlerTest extends Specification {

    def handler = new MatrixVerseInstructionHandler()
    def matrixFactory = Mock(MatrixFactory)
    def executionContext = Mock(ExecutionContext)
    def executionContextManager = Mock(ExecutionContextManager)

    def setup() {
        handler.setExecutionContext(executionContext)
        handler.setExecutionContextManager(executionContextManager)
    }

    def "Test supported instruction code"() {
        when:
        def result = handler.getSupportedInstructionCode()

        then:
        result == InstructionCode.MATRIX_VERSE
    }

    def "Test handle method"() {
        given:
        def ip = Mock(InstructionPointer)
        def instruction = make a(saveInstruction, with(code, InstructionCode.MATRIX_VERSE), with(arguments, 4))
        def matrixBuilder = Mock(MatrixBuilder)
        def objectData = new DoubleScalar(2.25D)
        def matrix = Stub(Matrix)
        ip.current() >> instruction

        when:
        handler.handle(ip)

        then:
        1 * executionContextManager.executionStackPop(executionContext, 4) >> [objectData]
        1 * matrixBuilder.appendRight(2.25D)
        1 * ip.increment()
        1 * matrixBuilder.build() >> matrix
        1 * executionContext.executionStackPush({ it == matrix } as Matrix<Double>)
    }
}
