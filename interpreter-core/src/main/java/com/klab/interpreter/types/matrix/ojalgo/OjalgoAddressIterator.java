package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.types.AddressIterator;
import com.klab.interpreter.types.ObjectData;
import org.ojalgo.matrix.store.MatrixStore;

import java.util.Iterator;

public class OjalgoAddressIterator<N extends Number> implements AddressIterator {
    private Iterator<N> iterator;
    private long dataLength;
    private MatrixStore<N> data;
    private Integer max;

    OjalgoAddressIterator(MatrixStore<N> data) {
        this.data = data;
        iterator = data.iterator();
        dataLength = data.count();
    }

    @Override
    public int max() {
        if (max == null) {
            max = 0;
            for (N n : data) {
                int i = n.intValue();
                if (i > max) {
                    max = i;
                }
            }
        }
        return this.max;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public int getNext() {
        return iterator.next().intValue();
    }

    @Override
    public long length() {
        return dataLength;
    }

    @Override
    public void reset() {
        iterator = data.iterator();
    }

    @Override
    public boolean isTemp() {
        return false;
    }

    @Override
    public void setTemp(boolean temp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ObjectData copy() {
        throw new UnsupportedOperationException();
    }
}
