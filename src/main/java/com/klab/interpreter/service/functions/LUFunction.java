package com.klab.interpreter.service.functions;

import com.klab.interpreter.service.LUResult;
import com.klab.interpreter.types.MultiOutput;
import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.converters.ConvertersHolder;
import com.klab.interpreter.types.matrix.Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class LUFunction extends AbstractInternalFunction {
    private ConvertersHolder convertersHolder;

    public LUFunction() {
        super(1, 1, "lu");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        NumericObject num = (NumericObject) data[0];
        NumericType convertClass = convertersHolder.scalarToMatrix(num.getNumericType());
        Matrix matrix = convertClass == null ? ((Matrix) num) : convertersHolder.convert(num, convertClass);
        LUResult lu = matrix.lu();
        if (expectedOutput <= 1) {
            return lu.getY();
        }
        MultiOutput output = new MultiOutput(Math.min(expectedOutput, 3));
        if (expectedOutput >= 2) {
            output.add(0, lu.getL());
            output.add(1, lu.getU());
        }
        if (expectedOutput >= 3) {
            output.add(2, lu.getP());
        }
        return output;
    }

    @Autowired
    public void setConvertersHolder(ConvertersHolder convertersHolder) {
        this.convertersHolder = convertersHolder;
    }

}
