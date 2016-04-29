package com.klab.interpreter.core.math.utils;

import com.klab.interpreter.types.Sizeable;

public class SizeUtils {

    public static boolean isVector(Sizeable sizeable) {
        return sizeable.getColumns() == 1L || sizeable.getRows() == 1;
    }

    public static long getLength(Sizeable sizeable) {
        return sizeable.getColumns() * sizeable.getRows();
    }

    public static boolean isScalar(Sizeable sizeable) {
        return sizeable.getRows() == 1 && sizeable.getColumns() == 1;
    }
}
