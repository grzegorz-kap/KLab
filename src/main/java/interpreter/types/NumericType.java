package interpreter.types;

public enum NumericType {
    INTEGER(0, null),
    FLOAT(1, null),
    DOUBLE(2, NumericPriority.DOUBLE),
    MATRIX_DOUBLE(3, NumericPriority.MATRIX_DOUBLE),
    COMPLEX_DOUBLE(4, NumericPriority.COMPLEX_DOUBLE),
    COMPLEX_MATRIX(5, NumericPriority.MATRIX_COMPLEX_DOUBLE);

    private int index;
    private NumericPriority numericPriority;

    NumericType(int index, NumericPriority numericPriority) {
        this.index = index;
        this.numericPriority = numericPriority;
    }

    public int getIndex() {
        return index;
    }

    public NumericPriority getNumericPriority() {
        return numericPriority;
    }

    public Integer getPriority() {
        return numericPriority.getPriority();
    }
}
