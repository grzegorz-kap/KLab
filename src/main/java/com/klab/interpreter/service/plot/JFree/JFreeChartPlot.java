package com.klab.interpreter.service.plot.JFree;

import com.klab.interpreter.service.plot.Plot;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

class JFreeChartPlot extends JFrame implements Plot {
    private JFreeChart chart;

    JFreeChartPlot(JFreeChart chart) {
        super("2D plot");
        this.chart = chart;
    }

    @Override
    public void showPlot() {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(640, 480));
        setContentPane(chartPanel);
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
        toFront();
    }

    @Override
    public void saveToFile(Path path) {
        throw new UnsupportedOperationException();
    }
}
