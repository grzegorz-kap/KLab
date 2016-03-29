package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;

public interface ForIterator extends ObjectData, Sizeable {
    boolean hasNext();

    ObjectData getNext();
}
