package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.ArithmeticOperationsFactory;
import interpreter.core.arithmetic.add.DoubleNumberAdder;
import interpreter.core.arithmetic.add.DoubleNumberSubtractor;
import interpreter.core.arithmetic.add.NumberAdder;
import interpreter.core.arithmetic.add.NumberSubtractor;
import interpreter.parsing.model.NumberType;
import org.springframework.stereotype.Service;

@Service
public class StandardArithmeticOperationsFactory implements ArithmeticOperationsFactory {

    private static NumberAdder[] numberAdders = new NumberAdder[NumberType.values().length];
    private static NumberSubtractor[] numberSubtractors = new NumberSubtractor[NumberType.values().length];

    static {
        numberAdders[NumberType.DOUBLE.getIndex()] = new DoubleNumberAdder();
    }

    static {
        numberSubtractors[NumberType.DOUBLE.getIndex()] = new DoubleNumberSubtractor();
    }

    @Override
    public NumberAdder getAdder(NumberType numberType) {
        return numberAdders[numberType.getIndex()];
    }

    @Override
    public NumberSubtractor getSubtractor(NumberType numberType) {
        return numberSubtractors[numberType.getIndex()];
    }
}
