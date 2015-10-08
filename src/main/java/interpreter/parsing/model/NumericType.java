package interpreter.parsing.model;

public enum NumericType {
    INTEGER(0),
    FLOAT(1),
    DOUBLE(2),
    MATRIX_DOUBLE(3);

    private int index;

    NumericType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
