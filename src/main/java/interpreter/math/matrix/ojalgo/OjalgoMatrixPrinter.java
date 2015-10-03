package interpreter.math.matrix.ojalgo;

import org.ojalgo.matrix.store.MatrixStore;

public class OjalgoMatrixPrinter {

    public static <T extends Number> String toString(MatrixStore<T> matrixStore) {
        StringBuilder retVal = new StringBuilder();
        int tmpRowDim = (int) matrixStore.countRows();
        int tmpColDim = (int) matrixStore.countColumns();
        retVal.append(' ').append('<').append(' ').append(tmpRowDim).append(' ').append('x').append(' ').append(tmpColDim).append(' ').append('>');
        if (tmpRowDim > 0 && tmpColDim > 0 && tmpRowDim <= 50 && tmpColDim <= 50 && tmpRowDim * tmpColDim <= 200) {
            retVal.append("\n").append(matrixStore.get(0L, 0L));

            int i;
            for (i = 1; i < tmpColDim; ++i) {
                retVal.append("\t").append(matrixStore.get(0L, (long) i));
            }

            for (i = 1; i < tmpRowDim; ++i) {
                retVal.append("\n").append(matrixStore.get((long) i, 0L));

                for (int j = 1; j < tmpColDim; ++j) {
                    retVal.append(",\t").append(matrixStore.get((long) i, (long) j));
                }
            }
        }
        return retVal.toString();
    }
}
