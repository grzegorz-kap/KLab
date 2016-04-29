package com.klab.interpreter.service.plot;

import java.nio.file.Path;

public interface Plot {
    void show();

    void saveToFile(Path path);
}