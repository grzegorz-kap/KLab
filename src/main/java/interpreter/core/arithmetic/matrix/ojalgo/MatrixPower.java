package interpreter.core.arithmetic.matrix.ojalgo;

import org.ojalgo.matrix.store.MatrixStore;

import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;

public class MatrixPower<N extends Number> extends OjalgoOperator<N> {
	public MatrixPower(OjalgoMatrixCreator<N> creator) {
		super(creator);
	}

	@Override
	protected MatrixStore<N> operate(OjalgoAbstractMatrix<N> first, OjalgoAbstractMatrix<N> second) {
		throw new UnsupportedOperationException();
	}
}
