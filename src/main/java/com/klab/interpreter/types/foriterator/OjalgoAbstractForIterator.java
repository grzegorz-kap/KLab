package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.converters.Converter;
import com.klab.interpreter.types.converters.OjalgoScalarComplexConverter;
import com.klab.interpreter.types.converters.OjalgoScalarDoubleConverter;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;

abstract class OjalgoAbstractForIterator<N extends Number> extends AbstractForIterator {
    Converter<?> converter;
    protected OjalgoAbstractMatrix<N> data;
    private long columns = 0;
    protected long rows = 0;
    long currentColumn = 0;

    OjalgoAbstractForIterator(OjalgoAbstractMatrix<N> data) {
        rows = data.getRows();
        columns = data.getColumns();
        this.data = data;
        converter = NumericType.COMPLEX_MATRIX.equals(data.getNumericType()) ? new OjalgoScalarComplexConverter() : new OjalgoScalarDoubleConverter();
    }

    @Override
    public long getRows() {
        return rows;
    }

    @Override
    public long getColumns() {
        return columns;
    }

    @Override
    public long getCells() {
        return data.getCells();
    }

    @Override
    public boolean hasNext() {
        return currentColumn < columns;
    }

    @Override
    public abstract ObjectData getNext();

    @Override
    public String toString() {
        return data.toString();
    }
}
