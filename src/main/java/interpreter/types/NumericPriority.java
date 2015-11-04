package interpreter.types;

public enum NumericPriority {
    DOUBLE(0, true),
    COMPLEX_DOUBLE(1, true, true),
    MATRIX_DOUBLE(2, false),
    MATRIX_COMPLEX_DOUBLE(3, false, true);

    private Integer priority;
    private boolean isScalar;
    private boolean isComplex;

    NumericPriority(Integer priority, boolean isScalar) {
        this(priority, isScalar, false);
    }

    NumericPriority(Integer priority, boolean isScalar, boolean isComplex) {
        this.priority = priority;
        this.isScalar = isScalar;
        this.isComplex = isComplex;
    }

    public Integer getPriority() {
        return priority;
    }

    public boolean isScalar() {
        return isScalar;
    }

    public boolean isComplex() {
        return isComplex;
    }
}
