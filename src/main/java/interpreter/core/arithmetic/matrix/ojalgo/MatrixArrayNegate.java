package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.NumericObject;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;

public class MatrixArrayNegate<N extends Number> {
	private OjalgoMatrixCreator<N> creator;
	private Class<? extends OjalgoAbstractMatrix<N>> clazz;
	
	public MatrixArrayNegate(OjalgoMatrixCreator<N> creator, Class<? extends OjalgoAbstractMatrix<N>> clazz) {
		this.creator = creator;
		this.clazz = clazz;
	}

	public OjalgoAbstractMatrix<N> operatate(NumericObject a) {
		return creator.create(clazz.cast(a).getLazyStore().negate());
	}
}
