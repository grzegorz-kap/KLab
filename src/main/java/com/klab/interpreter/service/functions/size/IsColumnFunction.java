package com.klab.interpreter.service.functions.size;

import com.klab.interpreter.service.functions.AbstractInternalFunction;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;
import org.springframework.stereotype.Component;

@Component
class IsColumnFunction extends AbstractInternalFunction {
    public IsColumnFunction() {
        super(1, 1, "iscolumn");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        return numberScalarFactory.getDouble(((Sizeable) data[0]).isColumn());
    }
}
