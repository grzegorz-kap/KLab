package com.klab.interpreter.commons;

import com.klab.interpreter.types.Timer;

public class MineTimer {
    private static final Timer TIMER = new Timer();

    private MineTimer() {
    }

    public static Timer getTIMER() {
        return TIMER;
    }
}
