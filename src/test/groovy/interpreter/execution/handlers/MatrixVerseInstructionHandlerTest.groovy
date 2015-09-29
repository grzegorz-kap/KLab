package interpreter.execution.handlers

import interpreter.execution.model.ExecutionContext
import interpreter.execution.model.InstructionPointer
import interpreter.math.matrix.Matrix
import interpreter.math.matrix.MatrixBuilder
import interpreter.math.matrix.MatrixFactory
import interpreter.math.scalar.DoubleScalar
import interpreter.translate.model.instruction.InstructionCode
import spock.lang.Specification

import static com.natpryce.makeiteasy.MakeItEasy.*
import static interpreter.translate.makers.InstructionMaker.*

class MatrixVerseInstructionHandlerTest extends Specification {

    def handler = new MatrixVerseInstructionHandler()
    def matrixFactory = Mock(MatrixFactory)
    def executionContext = Mock(ExecutionContext)

    def setup() {
        handler.setMatrixFactory(matrixFactory)
        handler.setExecutionContext(executionContext)
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
        1 * matrixFactory.createDoubleBuilder() >> matrixBuilder
        4 * executionContext.executionStackPop() >> objectData
        4 * matrixBuilder.appendRight(2.25D)
        1 * ip.increment()
        1 * matrixBuilder.build() >> matrix
        1 * executionContext.pushToExecutionStack({ it == matrix } as Matrix<Double>)
    }
}
