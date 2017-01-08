package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.execution.exception.WrongTypeException;
import com.klab.interpreter.execution.helper.NumericUtils;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.execution.service.ExecutionContextManager;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.MatrixBuilder;
import com.klab.interpreter.types.scalar.Scalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixVerseInstructionHandler extends AbstractInstructionHandler {
    private ExecutionContextManager executionContextManager;
    private NumericUtils numericUtils;

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MATRIX_VERSE;
    }

    @Override
    public void handle(InstructionPointer instructionPointer) {
        process(executionContextManager.executionStackPop(executionContext, instructionPointer.currentInstruction().getArgumentsNumber()));
        instructionPointer.increment();
    }

    private void process(List<ObjectData> objectDataList) {
        Matrix<? extends Number> matrix = numericUtils.createMatrix(objectDataList, this::process);
        executionContext.executionStackPush(matrix);
    }

    private void process(MatrixBuilder<?> builder, NumericObject numericObject) {
        if (numericObject instanceof Scalar) {
            builder.appendRight(((Scalar<?>) numericObject).getValue());
        } else if (numericObject instanceof Matrix) {
            builder.appendRight((Matrix) numericObject);
        } else {
            throw new WrongTypeException(numericObject);
        }
    }

    @Autowired
    public void setExecutionContextManager(ExecutionContextManager executionContextManager) {
        this.executionContextManager = executionContextManager;
    }

    @Autowired
    public void setNumericUtils(NumericUtils numericUtils) {
        this.numericUtils = numericUtils;
    }
}
