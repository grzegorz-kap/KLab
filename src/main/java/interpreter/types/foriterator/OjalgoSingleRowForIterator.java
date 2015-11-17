package interpreter.types.foriterator;

import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;

public class OjalgoSingleRowForIterator<N extends Number> extends OjalgoAbstractForIterator<N> {
    public OjalgoSingleRowForIterator(OjalgoAbstractMatrix<N> data) {
        super(data);
    }

    @Override
    public ObjectData getNext() {
        return converter.convert(data.get(0, currentColumn++));
    }
}
