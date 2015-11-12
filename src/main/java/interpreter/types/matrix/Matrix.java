package interpreter.types.matrix;

import interpreter.types.Indexable;
import interpreter.types.NumericObject;
import interpreter.types.Sizeable;

import java.util.function.Consumer;

public interface Matrix<T extends Number> extends NumericObject, Sizeable, Indexable {
    void set(int m, int n, T value);

    void forEach(Consumer<? super T> action);

    T getNumber(int i);
}
