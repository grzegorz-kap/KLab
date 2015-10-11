package interpreter.execution.handlers;

import interpreter.commons.ObjectData;
import interpreter.execution.WrongTypeException;
import interpreter.execution.model.InstructionPointer;
import interpreter.execution.service.ExecutionContextManager;
import interpreter.translate.model.instruction.InstructionCode;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixBuilder;
import interpreter.types.matrix.MatrixFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixInstructionHandler extends AbstractInstructionHandler {

    private MatrixFactory matrixFactory;
    private ExecutionContextManager executionContextManager;

    public void handle(InstructionPointer instructionPointer) {
        process(executionContextManager.executionStackPop(executionContext, instructionPointer.current().getArgumentsNumber()));
        instructionPointer.increment();
    }

    private void process(List<ObjectData> objectDataList) {
        MatrixBuilder<Double> matrixBuilder = matrixFactory.createDoubleBuilder();
        objectDataList.forEach(objectData -> process(objectData, matrixBuilder));
        executionContext.executionStackPush(matrixBuilder.build());
    }

    private void process(ObjectData objectData, MatrixBuilder<Double> matrixBuilder) {
        if (objectData instanceof Matrix) {
            matrixBuilder.appendBelow((Matrix) objectData);
        } else {
            throw new WrongTypeException(objectData);
        }
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MATRIX;
    }

    @Autowired
    public void setMatrixFactory(MatrixFactory matrixFactory) {
        this.matrixFactory = matrixFactory;
    }

    @Autowired
    public void setExecutionContextManager(ExecutionContextManager executionContextManager) {
        this.executionContextManager = executionContextManager;
    }
}
