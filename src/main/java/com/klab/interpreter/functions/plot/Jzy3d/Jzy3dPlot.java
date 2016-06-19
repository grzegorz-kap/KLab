package com.klab.interpreter.functions.plot.Jzy3d;

import com.klab.interpreter.functions.plot.Plot;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.ChartLauncher;

import java.nio.file.Path;

public class Jzy3dPlot implements Plot {
    private AWTChart awtChart;

    public Jzy3dPlot(AWTChart awtChart) {
        this.awtChart = awtChart;
    }

    @Override
    public void showPlot() {
        ChartLauncher.openChart(awtChart);
    }

    @Override
    public void saveToFile(Path path) {
        throw new UnsupportedOperationException();
    }
}