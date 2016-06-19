package com.klab.interpreter.functions.size;

import com.klab.interpreter.functions.AbstractInternalFunction;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;
import org.springframework.stereotype.Component;

@Component
class Columns extends AbstractInternalFunction {
    public Columns() {
        super(1, 1, "columns");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        return numberScalarFactory.getDouble(((Sizeable) data[0]).getColumns());
    }
}
