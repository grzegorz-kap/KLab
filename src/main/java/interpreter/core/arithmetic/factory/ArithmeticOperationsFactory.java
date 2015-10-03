package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.NumericObjectsAdder;
import interpreter.core.arithmetic.NumericObjectsDivider;
import interpreter.core.arithmetic.NumericObjectsMultiplicator;
import interpreter.core.arithmetic.NumericObjectsSubtractor;
import interpreter.parsing.model.NumericType;

public interface ArithmeticOperationsFactory {

    NumericObjectsAdder getAdder(NumericType numericType);

    NumericObjectsSubtractor getSubtractor(NumericType numericType);

    NumericObjectsMultiplicator getMultiplicator(NumericType numericType);

    NumericObjectsDivider getDivider(NumericType numericType);
}
