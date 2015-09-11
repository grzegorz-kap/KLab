package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.NumberAdder;
import interpreter.core.arithmetic.NumberDivider;
import interpreter.core.arithmetic.NumberMultiplicator;
import interpreter.core.arithmetic.NumberSubtractor;
import interpreter.core.arithmetic.scalar.ScalarDoubleNumberAdder;
import interpreter.core.arithmetic.scalar.ScalarDoubleNumberDivider;
import interpreter.core.arithmetic.scalar.ScalarDoubleNumberMultiplicator;
import interpreter.core.arithmetic.scalar.ScalarDoubleNumberSubtractor;
import interpreter.parsing.model.NumberType;
import org.springframework.stereotype.Service;

@Service
public class StandardArithmeticOperationsFactory implements ArithmeticOperationsFactory {

    private static final int NUMBER_TYPES_COUNT = NumberType.values().length;

    private static NumberAdder[] numberAdders = new NumberAdder[NUMBER_TYPES_COUNT];
    private static NumberSubtractor[] numberSubtractors = new NumberSubtractor[NUMBER_TYPES_COUNT];
    private static NumberMultiplicator[] numberMultiplicators = new NumberMultiplicator[NUMBER_TYPES_COUNT];
    private static NumberDivider[] numberDividers = new NumberDivider[NUMBER_TYPES_COUNT];

    static {
        numberAdders[NumberType.DOUBLE.getIndex()] = new ScalarDoubleNumberAdder();
    }

    static {
        numberSubtractors[NumberType.DOUBLE.getIndex()] = new ScalarDoubleNumberSubtractor();
    }

    static {
        numberMultiplicators[NumberType.DOUBLE.getIndex()] = new ScalarDoubleNumberMultiplicator();
    }

    static {
        numberDividers[NumberType.DOUBLE.getIndex()] = new ScalarDoubleNumberDivider();
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

    @Override
    public NumberDivider getDivider(NumberType numberType) {
        return numberDividers[numberType.getIndex()];
    }
}
