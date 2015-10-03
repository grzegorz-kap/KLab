package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.NumberAdder;
import interpreter.core.arithmetic.NumberDivider;
import interpreter.core.arithmetic.NumberMultiplicator;
import interpreter.core.arithmetic.NumberSubtractor;
import interpreter.parsing.model.NumericType;

public interface ArithmeticOperationsFactory {

    NumberAdder getAdder(NumericType numericType);

    NumberSubtractor getSubtractor(NumericType numericType);

    NumberMultiplicator getMultiplicator(NumericType numericType);

    NumberDivider getDivider(NumericType numericType);
}
