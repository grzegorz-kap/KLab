package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.NumberAdder;
import interpreter.core.arithmetic.NumberDivider;
import interpreter.core.arithmetic.NumberMultiplicator;
import interpreter.core.arithmetic.NumberSubtractor;
import interpreter.parsing.model.NumberType;

public interface ArithmeticOperationsFactory {

    NumberAdder getAdder(NumberType numberType);

    NumberSubtractor getSubtractor(NumberType numberType);

    NumberMultiplicator getMultiplicator(NumberType numberType);

    NumberDivider getDivider(NumberType numberType);
}
