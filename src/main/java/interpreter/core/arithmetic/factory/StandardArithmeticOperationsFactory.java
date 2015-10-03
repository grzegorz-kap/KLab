package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.*;
import interpreter.parsing.model.NumericType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class StandardArithmeticOperationsFactory implements ArithmeticOperationsFactory {

    private static final int NUMBER_TYPES_COUNT = NumericType.values().length;

    private NumberAdder[] numberAdders = new NumberAdder[NUMBER_TYPES_COUNT];
    private NumberSubtractor[] numberSubtractors = new NumberSubtractor[NUMBER_TYPES_COUNT];
    private NumberMultiplicator[] numberMultiplicators = new NumberMultiplicator[NUMBER_TYPES_COUNT];
    private NumberDivider[] numberDividers = new NumberDivider[NUMBER_TYPES_COUNT];

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

    @Autowired
    public void setNumberAdders(Set<NumberAdder> numberAdders) {
        setOperators(numberAdders, this.numberAdders);
    }

    @Autowired
    public void setNumberSubtractors(Set<NumberSubtractor> numberSubtractors) {
        setOperators(numberSubtractors, this.numberSubtractors);
    }

    @Autowired
    public void setNumberMultiplicators(Set<NumberMultiplicator> numberMultiplicators) {
        setOperators(numberMultiplicators, this.numberMultiplicators);
    }

    @Autowired
    public void setNumberDividers(Set<NumberDivider> numberDividers) {
        setOperators(numberDividers, this.numberDividers);
    }

    private void setOperators(Set<? extends NumberOperator> sources, NumberOperator[] destination) {
        sources.stream()
                .filter(numberOperator -> Objects.nonNull(numberOperator.getSupportedType()))
                .forEach(operator -> destination[operator.getSupportedType().getIndex()] = operator);
    }
}
