package interpreter.types.foriterator;

import interpreter.types.ObjectData;

public interface ForIterator extends ObjectData {
    boolean hasNext();
    ObjectData getNext();
}
