package interpreter.types.foriterator;


import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;

public class OjalgoForIteratorFactory {
    public static <T extends Number> ForIterator create(OjalgoAbstractMatrix<T> ojalgoMatrix) {
        if (ojalgoMatrix.getRows() <= 1) {
            return new OjalgoSingleRowForIterator<>(ojalgoMatrix);
        }
        return new OjalgoMultiRowForIterator<>(ojalgoMatrix);
    }
}
