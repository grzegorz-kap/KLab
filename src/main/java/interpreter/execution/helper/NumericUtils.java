package interpreter.execution.helper;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixBuilder;
import interpreter.types.matrix.MatrixFactoryHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static interpreter.types.NumericType.*;

@Component
public class NumericUtils {

    private MatrixFactoryHolder matrixFactoryHolder;

    public Matrix<? extends Number> createMatrix(List<ObjectData> objectDataList, NumericObjectProcessor processor) {
        List<NumericObject> numericObjects = castAllToNumeric(objectDataList);
        NumericType numericType = determineNumericType(numericObjects);

        MatrixBuilder<? extends Number> matrixBuilder = matrixFactoryHolder.get(numericType).builder();
        numericObjects.forEach(numeric -> processor.process(matrixBuilder, numeric));
        return matrixBuilder.build();
    }

    public List<NumericObject> castAllToNumeric(List<ObjectData> objectDataList) {
        try {
            return objectDataList.stream().map(data -> (NumericObject) data).collect(Collectors.toList());
        } catch (ClassCastException ex) {
            throw new InterpreterCastException("Cannot cast to numeric object");
        }
    }

    public NumericType determineNumericType(List<NumericObject> numericObjects) {
        return numericObjects.stream().anyMatch(this::evaluateToComplex) ? COMPLEX_MATRIX : MATRIX_DOUBLE;
    }

    private boolean evaluateToComplex(NumericObject n) {
        return COMPLEX_DOUBLE.equals(n.getNumericType()) || COMPLEX_MATRIX.equals(n.getNumericType());
    }

    @Autowired
    public void setMatrixFactoryHolder(MatrixFactoryHolder matrixFactoryHolder) {
        this.matrixFactoryHolder = matrixFactoryHolder;
    }

    @FunctionalInterface
    public interface NumericObjectProcessor {
        void process(MatrixBuilder<? extends Number> builder, NumericObject numericObject);
    }
}
