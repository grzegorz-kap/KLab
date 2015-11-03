package interpreter.types;

public enum NumericPriority {
    DOUBLE(0),
    COMPLEX_DOUBLE(1),
    MATRIX_DOUBLE(2),
    MATRIX_COMPLEX_DOUBLE(3);

    private Integer priority;

    NumericPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}
