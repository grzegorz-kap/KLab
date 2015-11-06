package interpreter.types.foriterator;

import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;

public class OjalgoSingleRowForIterator<N extends Number> extends OjalgoAbstractForIterator<N> {
    public OjalgoSingleRowForIterator(OjalgoMatrix<N> data) {
        super(data);
    }

    @Override
    public ObjectData getNext() {
        return converter.convert(data.get(0, currentColumn++));
    }
}
