package com.klab.interpreter.functions.size;

import com.klab.interpreter.functions.AbstractInternalFunction;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;
import org.springframework.stereotype.Component;

@Component
class IsRowFunction extends AbstractInternalFunction {
    public IsRowFunction() {
        super(1, 1, "isrow");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        return numberScalarFactory.getDouble(((Sizeable) data[0]).isRow());
    }
}
