package interpreter.math.matrix;

import interpreter.commons.ObjectData;

public interface Matrix<T extends Number> extends ObjectData {

    T getValueAt(int m, int n);

    void setValueAt(int m, int n, T value);

    long getRowsCount();

    long getColumnsCount();
}
