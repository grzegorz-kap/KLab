package interpreter.execution.helper;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.types.NumericObject;
import interpreter.types.NumericPriority;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixBuilder;
import interpreter.types.matrix.MatrixFactoryHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static interpreter.types.NumericType.*;

@Component
public class NumericUtils {

    private MatrixFactoryHolder matrixFactoryHolder;
    private Map<NumericType, NumericType> numericObjectMap = new EnumMap<>(NumericType.class);

    public NumericType resolveType(NumericObject a, NumericObject b) {
        if (isTypeEquals(a, b)) {
            return a.getNumericType();
        }
        NumericType numericType = a.getNumericType().getPriority().compareTo(b.getNumericType().getPriority()) >= 0 ? a.getNumericType() : b.getNumericType();
        NumericPriority priority = numericType.getNumericPriority();
        return !priority.isScalar() && anyComplex(a, b) ? NumericType.COMPLEX_MATRIX : numericType;
    }

    public boolean isTypeEquals(NumericObject a, NumericObject b) {
        return a.getNumericType().equals(b.getNumericType());
    }

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

    private boolean anyComplex(NumericObject a, NumericObject b) {
        return a.getNumericType().getNumericPriority().isComplex() || b.getNumericType().getNumericPriority().isComplex();
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
