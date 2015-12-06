package interpreter.types.matrix;

import interpreter.types.NumericType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MatrixFactoryHolder {

    private MatrixFactory<?>[] matrixFactories = new MatrixFactory[NumericType.values().length];

    public MatrixFactory<?> get(NumericType numericType) {
        return matrixFactories[numericType.getIndex()];
    }

    @Autowired
    private void setMatrixFactories(Set<MatrixFactory<?>> matrixFactories) {
        matrixFactories.forEach(matrixFactory -> this.matrixFactories[matrixFactory.supports().getIndex()] = matrixFactory);
    }
}
