package interpreter.execution.handlers;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.execution.exception.WrongTypeException;
import interpreter.execution.model.InstructionPointer;
import interpreter.execution.service.ExecutionContextManager;
import interpreter.translate.model.InstructionCode;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixBuilder;
import interpreter.types.matrix.MatrixFactoryHolder;
import interpreter.types.scalar.DoubleScalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static interpreter.types.NumericType.*;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixVerseInstructionHandler extends AbstractInstructionHandler {
    private ExecutionContextManager executionContextManager;
    private MatrixFactoryHolder matrixFactoryHolder;

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MATRIX_VERSE;
    }

    @Override
    public void handle(InstructionPointer instructionPointer) {
        process(executionContextManager.executionStackPop(executionContext, instructionPointer.current().getArgumentsNumber()));
        instructionPointer.increment();
    }

    private void process(List<ObjectData> objectDataList) {
        List<NumericObject> numericObjects = castAllToNumeric(objectDataList);
        NumericType numericType = determineNumericType(numericObjects);

        MatrixBuilder<Double> matrixBuilder = matrixFactoryHolder.get(numericType).builder();
        objectDataList.forEach(objectData -> process(matrixBuilder, objectData));
        executionContext.executionStackPush(matrixBuilder.build());
    }

    private NumericType determineNumericType(List<NumericObject> numericObjects) {
        return numericObjects.stream().anyMatch(this::evaluateToComplex) ? COMPLEX_MATRIX : MATRIX_DOUBLE;
    }

    private boolean evaluateToComplex(NumericObject n) {
        return COMPLEX_DOUBLE.equals(n.getNumericType()) || COMPLEX_MATRIX.equals(n.getNumericType());
    }

    private void process(MatrixBuilder<Double> builder, ObjectData objectData) {
        if (objectData instanceof DoubleScalar) {
            builder.appendRight(((DoubleScalar) objectData).getValue());
        } else if (objectData instanceof Matrix) {
            builder.appendRight((Matrix) objectData);
        } else {
            throw new WrongTypeException(objectData);
        }
    }

    private List<NumericObject> castAllToNumeric(List<ObjectData> objectDataList) {
        try {
            return objectDataList.stream().map(data -> (NumericObject) data).collect(Collectors.toList());
        } catch (ClassCastException ex) {
            throw new InterpreterCastException("Cannot cast to numeric object");
        }
    }

    @Autowired
    public void setExecutionContextManager(ExecutionContextManager executionContextManager) {
        this.executionContextManager = executionContextManager;
    }

    @Autowired
    public void setMatrixFactoryHolder(MatrixFactoryHolder matrixFactoryHolder) {
        this.matrixFactoryHolder = matrixFactoryHolder;
    }
}
