package com.klab.interpreter.commons;

import com.klab.interpreter.types.ObjectData;

public interface MemorySpace {
    void newScope(ObjectData[] data);

    void previousScope();

    void reserve(int size);

    void set(int address, ObjectData data);

    ObjectData get(int address);
}
