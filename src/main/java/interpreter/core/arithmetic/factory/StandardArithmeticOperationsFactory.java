package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.ArithmeticOperationsFactory;
import interpreter.core.arithmetic.add.*;
import interpreter.parsing.model.NumberType;
import org.springframework.stereotype.Service;

@Service
public class StandardArithmeticOperationsFactory implements ArithmeticOperationsFactory {

    private static final int NUMBER_TYPES_COUNT = NumberType.values().length;

    private static NumberAdder[] numberAdders = new NumberAdder[NUMBER_TYPES_COUNT];
    private static NumberSubtractor[] numberSubtractors = new NumberSubtractor[NUMBER_TYPES_COUNT];
    private static NumberMultiplicator[] numberMultiplicators = new NumberMultiplicator[NUMBER_TYPES_COUNT];

    static {
        numberAdders[NumberType.DOUBLE.getIndex()] = new DoubleNumberAdder();
    }

    static {
        numberSubtractors[NumberType.DOUBLE.getIndex()] = new DoubleNumberSubtractor();
    }

    static {
        numberMultiplicators[NumberType.DOUBLE.getIndex()] = new DoubleNumberMultiplicator();
    }

    @Override
    public NumberAdder getAdder(NumberType numberType) {
        return numberAdders[numberType.getIndex()];
    }

    @Override
    public NumberSubtractor getSubtractor(NumberType numberType) {
        return numberSubtractors[numberType.getIndex()];
    }

    @Override
    public NumberMultiplicator getMultiplicator(NumberType numberType) {
        return numberMultiplicators[numberType.getIndex()];
    }
}
