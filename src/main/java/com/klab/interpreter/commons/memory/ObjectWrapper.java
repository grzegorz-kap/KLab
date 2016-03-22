package com.klab.interpreter.commons.memory;

import com.klab.interpreter.types.ObjectData;

public class ObjectWrapper {
    protected ObjectData data;
    protected long version = 0;
    private final int address;

    ObjectWrapper(ObjectData data, int address) {
        this.data = data;
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public ObjectData getData() {
        return data;
    }

    public long getVersion() {
        return version;
    }
}
