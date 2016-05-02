package com.klab.interpreter.service.plot;

import java.nio.file.Path;

public interface Plot {
    void showPlot();

    void saveToFile(Path path);
}