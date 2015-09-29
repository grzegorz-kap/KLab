package interpreter.math.matrix;

import interpreter.commons.ObjectData;

public interface Matrix<T extends Number> extends ObjectData {

    T get(int m, int n);

    void set(int m, int n, T value);
}
