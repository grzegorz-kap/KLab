package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.*;
import interpreter.parsing.model.NumericType;

public interface OperatorExecutionFactory {

    NumericObjectsAdder getAdder(NumericType numericType);

    NumericObjectsSubtractor getSubtractor(NumericType numericType);

    NumericObjectsMultiplicator getMultiplicator(NumericType numericType);

    NumericObjectsDivider getDivider(NumericType numericType);

    NumericObjectsComparator getComporator(NumericType numericType);
}
