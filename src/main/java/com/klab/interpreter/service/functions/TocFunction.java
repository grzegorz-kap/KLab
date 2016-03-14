package com.klab.interpreter.service.functions;

import com.klab.interpreter.commons.MineTimer;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Timer;
import com.klab.interpreter.types.scalar.NumberScalarFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TocFunction extends AbstractInternalFunction {
    private NumberScalarFactory numberScalarFactory;

    public TocFunction() {
        super(0, 1, TOC);
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        Long interval = data.length == 1 ? ((Timer) data[0]).stop() : MineTimer.getTIMER().stop();
        return numberScalarFactory.getDouble(interval);
    }

    @Autowired
    public void setNumberScalarFactory(NumberScalarFactory numberScalarFactory) {
        this.numberScalarFactory = numberScalarFactory;
    }
}
