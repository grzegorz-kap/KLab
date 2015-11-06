package interpreter.types.foriterator;

import interpreter.types.matrix.ojalgo.OjalgoMatrix;

public class OjalgoForIteratorFactory {
    public static <T extends Number> ForIterator create(OjalgoMatrix<T> ojalgoMatrix) {
        if (ojalgoMatrix.getRows() <= 1) {
            return new OjalgoSingleRowForIterator<>(ojalgoMatrix);
        }
        return new OjalgoMultiRowForIterator<>(ojalgoMatrix);
    }
}
