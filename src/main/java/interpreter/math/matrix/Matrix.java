package interpreter.math.matrix;

import interpreter.math.NumericObject;

public interface Matrix<T extends Number> extends NumericObject {

    T getValueAt(int m, int n);

    void setValueAt(int m, int n, T value);

    long getRowsCount();

    long getColumnsCount();
}
