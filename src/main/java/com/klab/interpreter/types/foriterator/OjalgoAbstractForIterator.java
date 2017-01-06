package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.converters.Converter;
import com.klab.interpreter.types.converters.OjalgoScalarComplexConverter;
import com.klab.interpreter.types.converters.OjalgoScalarDoubleConverter;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;

abstract class OjalgoAbstractForIterator<N extends Number> extends AbstractForIterator {
    protected OjalgoAbstractMatrix<N> data;
    protected int rows = 0;
    Converter<?> converter;
    long currentColumn = 0;
    private int columns = 0;

    OjalgoAbstractForIterator(OjalgoAbstractMatrix<N> data) {
        rows = data.getRows();
        columns = data.getColumns();
        this.data = data;
        converter = NumericType.COMPLEX_MATRIX.equals(data.getNumericType()) ? new OjalgoScalarComplexConverter() : new OjalgoScalarDoubleConverter();
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
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
