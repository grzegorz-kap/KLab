package com.klab.interpreter.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiOutput implements ObjectData, Iterable<ObjectData> {
    private List<ObjectData> data = new ArrayList<>();

    public static MultiOutput build() {
        return new MultiOutput();
    }

    public MultiOutput add(ObjectData data) {
        this.data.add(data);
        return this;
    }

    public int size() {
        return data.size();
    }

    @Override
    public Iterator<ObjectData> iterator() {
        return data.iterator();
    }
}
