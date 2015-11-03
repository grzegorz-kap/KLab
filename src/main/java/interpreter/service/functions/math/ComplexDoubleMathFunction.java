package interpreter.service.functions.math;

import interpreter.parsing.model.NumericType;
import interpreter.types.NumericObject;
import interpreter.types.scalar.ComplexScalar;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Component;

import static org.ojalgo.function.ComplexFunction.*;

@Component
public class ComplexDoubleMathFunction implements MathFunctions {

    @Override
    public NumericType supports() {
        return NumericType.COMPLEX_DOUBLE;
    }

    @Override
    public NumericObject sqrt(NumericObject value) {
        return new ComplexScalar(SQRT.invoke(getComplex(value)));
    }

    @Override
    public NumericObject inv(NumericObject value) throws Exception {
        return new ComplexScalar(INVERT.invoke(getComplex(value)));
    }

    @Override
    public NumericObject sin(NumericObject value) {
        return new ComplexScalar(SIN.invoke(getComplex(value)));
    }

    @Override
    public NumericObject det(NumericObject value) {
        return value;
    }

    @Override
    public NumericObject tan(NumericObject value) {
        return new ComplexScalar(TAN.invoke(getComplex(value)));
    }

    @Override
    public NumericObject cos(NumericObject value) {
        return new ComplexScalar(COS.invoke(getComplex(value)));
    }

    protected ComplexNumber getComplex(NumericObject value) {
        return ((ComplexScalar) value).getComplex();
    }

}
