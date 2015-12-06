package interpreter.types.foriterator;

import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.converters.Converter;
import interpreter.types.converters.OjalgoScalarComplexConverter;
import interpreter.types.converters.OjalgoScalarDoubleConverter;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;

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
}
