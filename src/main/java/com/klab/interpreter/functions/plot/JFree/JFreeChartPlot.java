package com.klab.interpreter.functions.plot.JFree;

import com.klab.interpreter.functions.plot.Plot;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class JFreeChartPlot extends JFrame implements Plot {
    private JFreeChart chart;

    public JFreeChartPlot(String title, JFreeChart chart) {
        super(title);
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
