package com.klab.interpreter.service.plot;

import com.klab.interpreter.types.matrix.Matrix;

public interface PlotService {
    Plot create2D(Matrix<Double> x, Matrix<Double> y);

    Plot create3D(Matrix<Double> x, Matrix<Double> y, Matrix<Double> z);
}