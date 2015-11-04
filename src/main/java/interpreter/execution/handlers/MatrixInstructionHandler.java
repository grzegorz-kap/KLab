package interpreter.execution.handlers;

import interpreter.execution.exception.WrongTypeException;
import interpreter.execution.helper.NumericUtils;
import interpreter.execution.model.InstructionPointer;
import interpreter.execution.service.ExecutionContextManager;
import interpreter.translate.model.InstructionCode;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixInstructionHandler extends AbstractInstructionHandler {
    private ExecutionContextManager executionContextManager;
    private NumericUtils numericUtils;

    public void handle(InstructionPointer instructionPointer) {
        process(executionContextManager.executionStackPop(executionContext, instructionPointer.current().getArgumentsNumber()));
        instructionPointer.increment();
    }

    private void process(List<ObjectData> objectDataList) {
        Matrix<? extends Number> matrix = numericUtils.createMatrix(objectDataList, this::process);
        executionContext.executionStackPush(matrix);
    }

    private void process(MatrixBuilder<? extends Number> matrixBuilder, NumericObject numericObject) {
        if (numericObject instanceof Matrix) {
            matrixBuilder.appendBelow((Matrix<Number>) numericObject);
        } else {
            throw new WrongTypeException(numericObject);
        }
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MATRIX;
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
