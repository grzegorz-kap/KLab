package interpreter.parsing.model;

public enum NumberType {
    INTEGER(0),
    FLOAT(1),
    DOUBLE(2);

    private int index;

    NumberType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
