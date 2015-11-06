package interpreter.types.scalar;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import org.ojalgo.scalar.ComplexNumber;

import static interpreter.commons.exception.InterpreterCastException.COMPLEX_LOGICALS;

public class ComplexScalar extends AbstractScalar {
    private ComplexNumber value;

    public ComplexScalar(ComplexScalar c) {
        super(NumericType.COMPLEX_DOUBLE);
        value = new ComplexNumber(c.value.getReal(), c.value.getImaginary());
    }

    public ComplexScalar(ComplexNumber c) {
        super(NumericType.COMPLEX_DOUBLE);
        value = new ComplexNumber(c.getReal(), c.getImaginary());
    }

    public ComplexScalar(double re, double im) {
        super(NumericType.COMPLEX_DOUBLE);
        value = new ComplexNumber(re, im);
    }

    public ComplexScalar(Number numberValue) {
        super(NumericType.COMPLEX_DOUBLE);
        value = ComplexNumber.valueOf(numberValue);
    }

    public ComplexNumber getComplex() {
        return value;
    }

    @Override
    public Number getValue() {
        return value;
    }

    @Override
    public boolean isMathematicalInteger() {
        return value.getImaginary() == 0.0D && Math.rint(value.getReal()) == value.getReal();
    }

    @Override
    public boolean isTrue() {
        if (value.getImaginary() != 0.0D) {
            throw new InterpreterCastException(COMPLEX_LOGICALS);
        }
        return value.getReal() != 0.0D;
    }

    @Override
    public ObjectData copyObjectData() {
        return new ComplexScalar(this);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}