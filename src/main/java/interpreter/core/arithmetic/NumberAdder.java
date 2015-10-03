package interpreter.core.arithmetic;

import interpreter.commons.ObjectData;

public interface NumberAdder extends NumberOperator {

    ObjectData add(final ObjectData a, final ObjectData b);

}
