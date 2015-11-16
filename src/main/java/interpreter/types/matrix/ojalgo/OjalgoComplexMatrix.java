package interpreter.types.matrix.ojalgo;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.Scalar;
import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;

import static interpreter.commons.exception.InterpreterCastException.COMPLEX_LOGICALS;

public class OjalgoComplexMatrix extends OjalgoAbstractMatrix<ComplexNumber> {

    public OjalgoComplexMatrix(MatrixStore<ComplexNumber> store) {
        super(NumericType.COMPLEX_MATRIX);
        setLazyStore(store);
        setFactory(ComplexDenseStore.FACTORY);
    }

    @Override
    public boolean isTrue() {
        for (ComplexNumber value : getMatrixStore()) {
            if (value.getImaginary() != 0.0D) {
                throw new InterpreterCastException(COMPLEX_LOGICALS);
            }
            if (value.getReal() == 0.0D) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ObjectData copyObjectData() {
        return new OjalgoComplexMatrix(getLazyStore().copy());
    }


    @Override
    protected Scalar createScalar(Number number) {
        return new ComplexScalar(number);
    }
}
