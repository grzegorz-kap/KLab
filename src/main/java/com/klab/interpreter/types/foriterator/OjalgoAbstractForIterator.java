package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.converters.Converter;
import com.klab.interpreter.types.converters.OjalgoScalarComplexConverter;
import com.klab.interpreter.types.converters.OjalgoScalarDoubleConverter;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;

public abstract class OjalgoAbstractForIterator<N extends Number> extends AbstractForIterator {
    protected Converter<?> converter;
    protected OjalgoAbstractMatrix<N> data;
    protected long columns = 0;
    protected long rows = 0;
    protected long currentColumn = 0;

    public OjalgoAbstractForIterator(OjalgoAbstractMatrix<N> data) {
        rows = data.getRows();
        columns = data.getColumns();
        this.data = data;
        converter = getConverter(data);
    }

    @Override
    public boolean hasNext() {
        return currentColumn < columns;
    }

    @Override
    public abstract ObjectData getNext();

    protected Converter<?> getConverter(OjalgoAbstractMatrix<N> data) {
        return NumericType.COMPLEX_MATRIX.equals(data.getNumericType()) ? new OjalgoScalarComplexConverter() : new OjalgoScalarDoubleConverter();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
