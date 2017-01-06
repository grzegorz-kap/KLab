package com.klab.interpreter.functions.math;

import com.klab.interpreter.types.ObjectData;

public interface LUResult {
    ObjectData getL();

    ObjectData getU();

    ObjectData getP();

    ObjectData getY();
}
