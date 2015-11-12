package interpreter.types;

public interface Indexable extends Sizeable {
    ObjectData get(int row, int column);

    ObjectData get(int cell);
}
