package com.klab.interpreter.service.functions.size;

import com.klab.interpreter.service.functions.AbstractInternalFunction;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;
import org.springframework.stereotype.Component;

@Component
class LengthFunction extends AbstractInternalFunction {
    public LengthFunction() {
        super(1, 1, "length");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        Sizeable sizeable = (Sizeable) data[0];
        return numberScalarFactory.getDouble(Math.max(sizeable.getColumns(), sizeable.getRows()));
    }
}
