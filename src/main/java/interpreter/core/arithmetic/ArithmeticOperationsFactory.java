package interpreter.core.arithmetic;

import interpreter.core.arithmetic.add.NumberAdder;
import interpreter.core.arithmetic.add.NumberMultiplicator;
import interpreter.core.arithmetic.add.NumberSubtractor;
import interpreter.parsing.model.NumberType;

public interface ArithmeticOperationsFactory {

    NumberAdder getAdder(NumberType numberType);

    NumberSubtractor getSubtractor(NumberType numberType);

    NumberMultiplicator getMultiplicator(NumberType numberType);
}
