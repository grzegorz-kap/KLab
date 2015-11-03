package interpreter.parsing.model;

public enum NumericType {
    INTEGER(0),
    FLOAT(1),
    DOUBLE(2),
    MATRIX_DOUBLE(3),
    COMPLEX_DOUBLE(4),
    COMPLEX_MATRIX(5);

    private int index;

    NumericType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
