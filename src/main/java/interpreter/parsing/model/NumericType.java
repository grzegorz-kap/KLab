package interpreter.parsing.model;

public enum NumericType {
    INTEGER(0),
    FLOAT(1),
    DOUBLE(2);

    private int index;

    NumericType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
