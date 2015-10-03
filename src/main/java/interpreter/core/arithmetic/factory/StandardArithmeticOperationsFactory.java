package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.NumberAdder;
import interpreter.core.arithmetic.NumberDivider;
import interpreter.core.arithmetic.NumberMultiplicator;
import interpreter.core.arithmetic.NumberSubtractor;
import interpreter.core.arithmetic.scalar.ScalarDoubleNumberAdder;
import interpreter.core.arithmetic.scalar.ScalarDoubleNumberDivider;
import interpreter.core.arithmetic.scalar.ScalarDoubleNumberMultiplicator;
import interpreter.core.arithmetic.scalar.ScalarDoubleNumberSubtractor;
import interpreter.parsing.model.NumericType;
import org.springframework.stereotype.Service;

@Service
public class StandardArithmeticOperationsFactory implements ArithmeticOperationsFactory {

    private static final int NUMBER_TYPES_COUNT = NumericType.values().length;

    private static NumberAdder[] numberAdders = new NumberAdder[NUMBER_TYPES_COUNT];
    private static NumberSubtractor[] numberSubtractors = new NumberSubtractor[NUMBER_TYPES_COUNT];
    private static NumberMultiplicator[] numberMultiplicators = new NumberMultiplicator[NUMBER_TYPES_COUNT];
    private static NumberDivider[] numberDividers = new NumberDivider[NUMBER_TYPES_COUNT];

    static {
        numberAdders[NumericType.DOUBLE.getIndex()] = new ScalarDoubleNumberAdder();
    }

    static {
        numberSubtractors[NumericType.DOUBLE.getIndex()] = new ScalarDoubleNumberSubtractor();
    }

    static {
        numberMultiplicators[NumericType.DOUBLE.getIndex()] = new ScalarDoubleNumberMultiplicator();
    }

    static {
        numberDividers[NumericType.DOUBLE.getIndex()] = new ScalarDoubleNumberDivider();
    }

    @Override
    public NumberAdder getAdder(NumericType numericType) {
        return numberAdders[numericType.getIndex()];
    }

    @Override
    public NumberSubtractor getSubtractor(NumericType numericType) {
        return numberSubtractors[numericType.getIndex()];
    }

    @Override
    public NumberMultiplicator getMultiplicator(NumericType numericType) {
        return numberMultiplicators[numericType.getIndex()];
    }

    @Override
    public NumberDivider getDivider(NumericType numericType) {
        return numberDividers[numericType.getIndex()];
    }
}
