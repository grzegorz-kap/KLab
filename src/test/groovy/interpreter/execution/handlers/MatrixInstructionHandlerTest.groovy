package com.klab.interpreter.execution.handlers

import com.klab.interpreter.execution.model.ExecutionContext
import com.klab.interpreter.execution.model.InstructionPointer
import com.klab.interpreter.execution.service.ExecutionContextManager
import com.klab.interpreter.translate.model.InstructionCode
import com.klab.interpreter.types.matrix.Matrix
import com.klab.interpreter.types.matrix.MatrixBuilder
import com.klab.interpreter.types.matrix.MatrixFactory
import spock.lang.Specification

import static com.klab.interpreter.translate.makers.InstructionMaker.*
import static com.natpryce.makeiteasy.MakeItEasy.*

class MatrixInstructionHandlerTest extends Specification {

    def handler = new MatrixInstructionHandler()
    def matrixFactory = Mock(MatrixFactory)
    def executionContextManager = Mock(ExecutionContextManager)
    def executionContext = Mock(ExecutionContext)

    def setup() {
        handler.setExecutionContextManager(executionContextManager)
        handler.setMatrixFactory(matrixFactory)
        handler.setExecutionContext(executionContext)
    }

    def "Test supported instruction code"() {
        when:
        def code = handler.getSupportedInstructionCode()
        then:
        code == InstructionCode.MATRIX
    }

    def "Test handle method"() {
        given:
        def instruction = make a(saveInstruction, with(code, InstructionCode.MATRIX), with(arguments, 4))
        def instructionPointer = Mock(InstructionPointer)
        def objectData = Stub(Matrix)
        def buildedMatrix = Stub(Matrix)
        def builder = Mock(MatrixBuilder)

        when:
        handler.handle(instructionPointer)

        then:
        1 * instructionPointer.currentInstruction() >> instruction
        1 * executionContextManager.executionStackPop(executionContext, instruction.argumentsNumber) >> [objectData]
        1 * matrixFactory.createDoubleBuilder() >> builder
        1 * builder.appendBelow(objectData)
        1 * builder.build() >> buildedMatrix
        1 * executionContext.executionStackPush(buildedMatrix)
    }
}
