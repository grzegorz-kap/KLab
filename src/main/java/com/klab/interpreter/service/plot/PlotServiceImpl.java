package com.klab.interpreter.service.plot;

import com.klab.interpreter.commons.exception.InterpreterException;
import com.klab.interpreter.core.math.utils.SizeUtils;
import com.klab.interpreter.service.plot.JFree.JFreeChartPlot;
import com.klab.interpreter.service.plot.Jzy3d.Jzy3dPlot;
import com.klab.interpreter.types.matrix.Matrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.chart.Settings;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Arrays;


@Service
public class PlotServiceImpl implements PlotService {
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
        return new JFreeChartPlot("2D plot", xyLineChart);
    }

    @Override
    public Plot create3D(Matrix<Double> x, Matrix<Double> y, Matrix<Double> z) {
        checkSizes3d(x, y, z);
        int cells = (int) x.getCells();

        Coord3d[] points = new Coord3d[cells];
        for (int i = 0; i < cells; i++) {
            points[i] = new Coord3d(x.get(i), y.get(i), z.get(i));
        }

        Settings.getInstance().setHardwareAccelerated(true);
        ChartLauncher.instructions();

        LineStrip lineStrip = new LineStrip(Arrays.asList(points));
        lineStrip.setShowPoints(true);
        lineStrip.setDisplayed(true);
        lineStrip.setWidth(5.0f);
        lineStrip.setWireframeColor(org.jzy3d.colors.Color.RED);

        AWTChart awtChart = new AWTChart(Quality.Advanced);
        awtChart.add(lineStrip);
        return new Jzy3dPlot(awtChart);
    }

    private void checkSizes3d(Matrix<Double> x, Matrix<Double> y, Matrix<Double> z) {
        if (!SizeUtils.isVector(x) || !SizeUtils.isVector(y) || !SizeUtils.isVector(z)) {
            throw new InterpreterException("plot3(x,y) - two arguments must be vectors");
        }
        if (x.getCells() != y.getCells() || x.getCells() != z.getCells()) {
            throw new InterpreterException("plot3(x,y) - arguments must have the same size");
        }
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
