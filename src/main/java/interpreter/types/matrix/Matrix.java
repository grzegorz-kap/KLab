package interpreter.types.matrix;

import interpreter.types.*;
import interpreter.types.foriterator.ForIterable;

import java.util.function.Consumer;

public interface Matrix<T extends Number> extends NumericObject, Sizeable, ForIterable,
        Indexable, Negable<Matrix<T>>, Editable<T>, EditSupportable<T> {

    T get(long m, long n);

    T get(int m);

    void set(int m, int n, T value);

    void forEach(Consumer<? super T> action);

    T getNumber(int i);
}
