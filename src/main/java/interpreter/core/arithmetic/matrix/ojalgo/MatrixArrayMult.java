package interpreter.core.arithmetic.matrix.ojalgo;

import org.ojalgo.matrix.store.MatrixStore;

import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;

public class MatrixArrayMult<N extends Number> extends OjalgoOperator<N> {
	public MatrixArrayMult(OjalgoMatrixCreator<N> creator) {
		super(creator);
	}

	@Override
	protected MatrixStore<N> operate(OjalgoAbstractMatrix<N> first, OjalgoAbstractMatrix<N> second) {
		MatrixStore<N> f = first.isScalar() ? new OjalgoMatrixScalarWrapper<>(first,second) : first.getLazyStore();
		MatrixStore<N> s = second.isScalar() ? new OjalgoMatrixScalarWrapper<>(second) : second.getLazyStore();
		return f.operateOnMatching(s.factory().function().multiply(), s);
	}
}
