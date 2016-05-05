package com.klab.interpreter.service.functions;

import com.klab.interpreter.commons.exception.InterpreterException;
import com.klab.interpreter.service.interpolation.LagrangeInterpolator;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.converters.ConvertersHolder;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.scalar.Scalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolyfitFunction extends AbstractInternalFunction {
    private LagrangeInterpolator<Double> lagrangeInterpolator;
    private ConvertersHolder convertersHolder;

    public PolyfitFunction() {
        super(3, 3, "polyfit");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        try {
            Matrix<Double> x = convertersHolder.convert(data[0], lagrangeInterpolator.supports());
            Matrix<Double> y = convertersHolder.convert(data[1], lagrangeInterpolator.supports());

            // TODO better to int cast
            NumericObject degree = (NumericObject) data[2];
            int degreeVal;
            if (degree instanceof Scalar) {
                degreeVal = ((Scalar) degree).getValue().intValue();
            } else if (degree instanceof Matrix) {
                degreeVal = ((Matrix) degree).get(0).intValue();
            } else {
                throw new RuntimeException();
            }

            return lagrangeInterpolator.interpolate(x, y, degreeVal);
        } catch (ClassCastException ex) {
            throw new InterpreterException(ex);
        }
    }

    @Autowired
    public void setLagrangeInterpolator(LagrangeInterpolator<Double> lagrangeInterpolator) {
        this.lagrangeInterpolator = lagrangeInterpolator;
    }

    @Autowired
    public void setConvertersHolder(ConvertersHolder convertersHolder) {
        this.convertersHolder = convertersHolder;
    }
}