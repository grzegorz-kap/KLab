package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;

class OjalgoSingleRowForIterator<N extends Number> extends OjalgoAbstractForIterator<N> {
    OjalgoSingleRowForIterator(OjalgoAbstractMatrix<N> data) {
        super(data);
    }

    @Override
    public ObjectData getNext() {
        return converter.convert(data.get(0, currentColumn++));
    }
}
