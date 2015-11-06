package interpreter.types.foriterator;

import interpreter.types.ObjectData;

public interface ForIterator {
    boolean hasNext();
    ObjectData getNext();
}
