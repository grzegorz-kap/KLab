package com.klab.interpreter.functions;

import com.klab.interpreter.commons.MineTimer;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Timer;
import org.springframework.stereotype.Component;

@Component
public class TicFunction extends AbstractInternalFunction {
    public TicFunction() {
        super(0, 0, TIC);
    }

    @Override
    public ObjectData call(ObjectData[] data, int outputSize) {
        if (outputSize <= 0) {
            MineTimer.getTIMER().start();
            return null;
        } else {
            Timer timer = new Timer();
            timer.start();
            return timer;
        }
    }
}
