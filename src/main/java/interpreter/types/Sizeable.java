package interpreter.types;

public interface Sizeable {

    long getRows();

    long getColumns();

    long getCells();

    default boolean isScalar() {
        return getRows() == 1 && getColumns() == 1;
    }

}
