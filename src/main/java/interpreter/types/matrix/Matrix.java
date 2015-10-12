package interpreter.types.matrix;

import interpreter.types.NumericObject;

public interface Matrix<T extends Number> extends NumericObject {

    T getValueAt(int m, int n);

    void setValueAt(int m, int n, T value);

    long getRowsCount();

    long getColumnsCount();
}
