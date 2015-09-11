package interpreter.core.arithmetic;

import interpreter.core.arithmetic.add.NumberAdder;
import interpreter.parsing.model.NumberType;

public interface ArithmeticOperationsFactory {

    NumberAdder getAdder(NumberType numberType);
}
