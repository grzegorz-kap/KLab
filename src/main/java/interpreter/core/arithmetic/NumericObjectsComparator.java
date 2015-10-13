package interpreter.core.arithmetic;

import interpreter.types.ObjectData;

public interface NumericObjectsComparator extends NumericObjectsOperator {

    ObjectData eq(ObjectData a, ObjectData b);

    ObjectData neq(ObjectData a, ObjectData b);

    ObjectData gt(ObjectData a, ObjectData b);
}
