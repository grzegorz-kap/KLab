package interpreter.types;

public interface Sizeable {

    long getRows();

    long getColumns();

    default boolean isScalar() {
        return getRows() == 1 && getColumns() == 1;
    }

}
