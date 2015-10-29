package interpreter.types.matrix;

import java.util.function.Consumer;

import interpreter.types.NumericObject;
import interpreter.types.Sizeable;

public interface Matrix<T extends Number> extends NumericObject, Sizeable {

    T get(int m, int n);
    
    T get(int m);

    void set(int m, int n, T value);

    long getRowsCount();

    long getColumnsCount();
    
    public void forEach(Consumer<? super T> action);
    
    
}
