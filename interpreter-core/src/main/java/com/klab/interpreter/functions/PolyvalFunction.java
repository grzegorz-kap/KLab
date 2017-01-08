package com.klab.interpreter.functions;

import com.klab.interpreter.functions.interpolation.PolynomialEvaluator;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.converters.ConvertersHolder;
import com.klab.interpreter.types.matrix.Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolyvalFunction extends AbstractInternalFunction {
    private ConvertersHolder convertersHolder;
    private PolynomialEvaluator polynomialEvaluator;

    public PolyvalFunction() {
        super(2, 2, "polyval");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        Matrix<Number> p = convertersHolder.convert(data[0], polynomialEvaluator.supports());
        Matrix<Number> x = convertersHolder.convert(data[1], polynomialEvaluator.supports());
        return polynomialEvaluator.evaluate(p, x);
    }

    @Autowired
    public void setPolynomialEvaluator(PolynomialEvaluator polynomialEvaluator) {
        this.polynomialEvaluator = polynomialEvaluator;
    }

    @Autowired
    public void setConvertersHolder(ConvertersHolder convertersHolder) {
        this.convertersHolder = convertersHolder;
    }
}
