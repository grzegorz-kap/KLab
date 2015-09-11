package interpreter.core.arithmetic;

import interpreter.parsing.model.NumberType;

public interface ArithmeticOperationsFactory {

    NumberAdder getAdder(NumberType numberType);

    NumberSubtractor getSubtractor(NumberType numberType);

    NumberMultiplicator getMultiplicator(NumberType numberType);
}
