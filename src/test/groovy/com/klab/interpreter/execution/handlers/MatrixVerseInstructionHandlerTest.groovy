package com.klab.interpreter.execution.handlers

import com.klab.interpreter.execution.model.ExecutionContext
import com.klab.interpreter.execution.model.InstructionPointer
import com.klab.interpreter.execution.service.ExecutionContextManager
import com.klab.interpreter.translate.model.InstructionCode
import com.klab.interpreter.types.matrix.Matrix
import com.klab.interpreter.types.matrix.MatrixBuilder
import com.klab.interpreter.types.matrix.MatrixFactory
import com.klab.interpreter.types.scalar.DoubleScalar
import spock.lang.Specification

import static com.klab.interpreter.translate.makers.InstructionMaker.*
import static com.natpryce.makeiteasy.MakeItEasy.*

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
        ip.currentInstruction() >> instruction

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
