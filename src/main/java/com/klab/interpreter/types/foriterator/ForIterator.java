package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.ObjectData;

public interface ForIterator extends ObjectData {
    boolean hasNext();

    ObjectData getNext();
}
