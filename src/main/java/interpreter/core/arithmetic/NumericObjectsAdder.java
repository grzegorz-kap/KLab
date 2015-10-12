package interpreter.core.arithmetic;

import interpreter.types.ObjectData;

public interface NumericObjectsAdder extends NumericObjectsOperator {

    ObjectData add(final ObjectData a, final ObjectData b);

}
