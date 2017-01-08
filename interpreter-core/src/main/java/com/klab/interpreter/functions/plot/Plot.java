package com.klab.interpreter.functions.plot;

import java.nio.file.Path;

public interface Plot {
    void showPlot();

    void saveToFile(Path path);
}