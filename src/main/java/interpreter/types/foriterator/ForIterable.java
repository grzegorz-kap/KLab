package interpreter.types.foriterator;

import interpreter.types.ObjectData;

public interface ForIterable extends ObjectData {
    ForIterator getIterator();
}
