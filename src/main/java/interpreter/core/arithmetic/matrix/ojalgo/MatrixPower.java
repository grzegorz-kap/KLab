package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.matrix.store.MatrixStore;

public class MatrixPower<N extends Number> extends OjalgoOperator<N> {
	public MatrixPower(OjalgoMatrixCreator<N> creator) {
		super(creator);
	}

	// TODO custom size checks
	@Override
	protected MatrixStore<N> operate(OjalgoAbstractMatrix<N> first, OjalgoAbstractMatrix<N> second) {
		// TODO more optimal algorithm
		if (second.isScalar() && Math.rint(second.get(0).doubleValue()) == second.get(0).doubleValue()) {
			long n = second.get(0).longValue();
			if (n == 0) {
				return first.getLazyStore().factory().makeEye(first.getRows(), first.getColumns());
			}
			if (n < 0) {
				throw new UnsupportedOperationException("Not supported yet!"); // TODO
																				// invert
																				// matrix
			}
			MatrixStore<N> result = first.getLazyStore();
			for (int i = 1; i < n; i++) {
				result = result.multiply(result);
			}
			return result;

		} // TODO else
		throw new UnsupportedOperationException();
	}
}
