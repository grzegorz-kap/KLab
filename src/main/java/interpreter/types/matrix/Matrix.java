package interpreter.types.matrix;

import interpreter.types.NumericObject;
import interpreter.types.Sizeable;

import java.util.function.Consumer;

public interface Matrix<T extends Number> extends NumericObject, Sizeable {

    T get(int m, int n);

    T get(int m);

    void set(int m, int n, T value);

    void forEach(Consumer<? super T> action);

}
