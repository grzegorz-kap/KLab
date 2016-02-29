package interpreter.commons;

import interpreter.types.Timer;

public class MineTimer {
    private static final Timer TIMER = new Timer();

    public static Timer getTIMER() {
        return TIMER;
    }

    private MineTimer() {
    }
}
