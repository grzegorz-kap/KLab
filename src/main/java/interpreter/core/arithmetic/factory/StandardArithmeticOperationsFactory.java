package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.ArithmeticOperationsFactory;
import interpreter.core.arithmetic.add.DoubleNumberAdder;
import interpreter.core.arithmetic.add.NumberAdder;
import interpreter.parsing.model.NumberType;
import org.springframework.stereotype.Service;

@Service
public class StandardArithmeticOperationsFactory implements ArithmeticOperationsFactory {

    private static NumberAdder[] numberAdders = new NumberAdder[NumberType.values().length];

    static {
        numberAdders[NumberType.DOUBLE.getIndex()] = new DoubleNumberAdder();
    }

    @Override
    public NumberAdder getAdder(NumberType numberType) {
        return numberAdders[numberType.getIndex()];
    }
}
