package interpreter.math.matrix;

public interface Matrix<T extends Number> {

    T get(int m, int n);

    void set(int m, int n, T value);
}
