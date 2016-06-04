package com.klab.interpreter.types;

import com.google.common.collect.Iterators;

import java.util.Iterator;

public class MultiOutput implements ObjectData, Iterable<ObjectData> {
    private ObjectData[] data;

    public MultiOutput(int length) {
        this.data = new ObjectData[length];
    }

    public MultiOutput add(int index, ObjectData data) {
        this.data[index] = data;
        return this;
    }

    public ObjectData get(int index) {
        return data[index];
    }

    public int size() {
        return data.length;
    }

    @Override
    public Iterator<ObjectData> iterator() {
        return Iterators.forArray(data);
    }

    @Override
    public void setAddress(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAddress() {
        return 0;
    }

    @Override
    public ObjectData copy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTemp() {
        return true;
    }

    @Override
    public void setTemp(boolean temp) {
        throw new UnsupportedOperationException();
    }
}
