package com.klab.interpreter.commons;

import com.klab.interpreter.types.ObjectData;

import java.util.stream.Stream;

public interface MemorySpace {
    void newScope(ObjectData[] data);

    void previousScope();

    void reserve(int size);

    void set(int address, ObjectData data);

    void set(int address, ObjectData data, String name);

    ObjectData get(int address);

    Stream<ObjectData> listCurrentScopeVariables();
}
