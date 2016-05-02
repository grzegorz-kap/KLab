package com.klab.interpreter.service.plot;

import com.klab.interpreter.commons.exception.InterpreterException;
import com.klab.interpreter.service.functions.AbstractInternalFunction;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.converters.ConvertersHolder;
import com.klab.interpreter.types.matrix.Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Plot3DFunction extends AbstractInternalFunction {
    private PlotService plotService;
    private ConvertersHolder convertersHolder;

    public Plot3DFunction() {
        super(3, 3, "plot3");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        try {
            Matrix<Double> x = convertersHolder.convert(data[0], NumericType.MATRIX_DOUBLE);
            Matrix<Double> y = convertersHolder.convert(data[1], NumericType.MATRIX_DOUBLE);
            Matrix<Double> z = convertersHolder.convert(data[2], NumericType.MATRIX_DOUBLE);
            Plot plot = plotService.create3D(x, y, z);
            plot.showPlot();
            return null;
        } catch (ClassCastException ex) {
            throw new InterpreterException(ex);
        }
    }

    @Autowired
    public void setPlotService(PlotService plotService) {
        this.plotService = plotService;
    }

    @Autowired
    public void setConvertersHolder(ConvertersHolder convertersHolder) {
        this.convertersHolder = convertersHolder;
    }
}