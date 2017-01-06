package com.klab.interpreter.commons.memory;

import com.klab.interpreter.types.ObjectData;

public class ObjectWrapper {
    private final int address;
    protected ObjectData data;
    protected long version = 0;

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
