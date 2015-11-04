package interpreter.types.matrix.ojalgo;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.types.NumericType;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;

import static interpreter.commons.exception.InterpreterCastException.COMPLEX_LOGICALS;

public class OjalgoComplexMatrix extends OjalgoMatrix<ComplexNumber> {

    public OjalgoComplexMatrix(MatrixStore<ComplexNumber> store) {
        super(store);
        setNumericType(NumericType.COMPLEX_MATRIX);
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
}
