package com.klab.interpreter.service.plot.JFree;

import com.klab.interpreter.commons.exception.InterpreterException;
import com.klab.interpreter.core.math.utils.SizeUtils;
import com.klab.interpreter.service.plot.Plot;
import com.klab.interpreter.service.plot.PlotService;
import com.klab.interpreter.types.matrix.Matrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class JFreeChartPlotService implements PlotService {
    @Override
    public Plot create2D(Matrix<Double> x, Matrix<Double> y) {
        checkSizes2D(x, y);
        final int cells = (int) x.getCells();

        XYSeries xySeries = new XYSeries("function");
        for (int i = 0; i < cells; i++) {
            xySeries.add(x.get(i), y.get(i));
        }

        JFreeChart xyLineChart = ChartFactory.createXYLineChart("f(x)", "x", "y", new XYSeriesCollection(xySeries));

        XYPlot xyPlot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        xyPlot.setRenderer(renderer);

        return new JFreeChartPlot(xyLineChart);
    }

    private void checkSizes2D(Matrix<Double> x, Matrix<Double> y) {
        if (!SizeUtils.isVector(x) || !SizeUtils.isVector(y)) {
            throw new InterpreterException("plot(x,y) - two arguments must be vectors");
        }
        if (x.getCells() != y.getCells()) {
            throw new InterpreterException("plot(x,y) - arguments must have the same size");
        }
    }
}
