package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.*;
import interpreter.parsing.model.NumericType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class StandardOperatorExecutionFactory implements OperatorExecutionFactory {

    private static final int NUMBER_TYPES_COUNT = NumericType.values().length;

    private NumericObjectsAdder[] numericObjectsAdders = new NumericObjectsAdder[NUMBER_TYPES_COUNT];
    private NumericObjectsSubtractor[] numericObjectsSubtractors = new NumericObjectsSubtractor[NUMBER_TYPES_COUNT];
    private NumericObjectsMultiplicator[] numberMultiplicators = new NumericObjectsMultiplicator[NUMBER_TYPES_COUNT];
    private NumericObjectsDivider[] numberDividers = new NumericObjectsDivider[NUMBER_TYPES_COUNT];

    @Override
    public NumericObjectsAdder getAdder(NumericType numericType) {
        return numericObjectsAdders[numericType.getIndex()];
    }

    @Override
    public NumericObjectsSubtractor getSubtractor(NumericType numericType) {
        return numericObjectsSubtractors[numericType.getIndex()];
    }

    @Override
    public NumericObjectsMultiplicator getMultiplicator(NumericType numericType) {
        return numberMultiplicators[numericType.getIndex()];
    }

    @Override
    public NumericObjectsDivider getDivider(NumericType numericType) {
        return numberDividers[numericType.getIndex()];
    }

    @Autowired
    public void setNumericObjectsAdders(Set<NumericObjectsAdder> numericObjectsAdders) {
        setOperators(numericObjectsAdders, this.numericObjectsAdders);
    }

    @Autowired
    public void setNumericObjectsSubtractors(Set<NumericObjectsSubtractor> numericObjectsSubtractors) {
        setOperators(numericObjectsSubtractors, this.numericObjectsSubtractors);
    }

    @Autowired
    public void setNumberMultiplicators(Set<NumericObjectsMultiplicator> numberMultiplicators) {
        setOperators(numberMultiplicators, this.numberMultiplicators);
    }

    @Autowired
    public void setNumberDividers(Set<NumericObjectsDivider> numberDividers) {
        setOperators(numberDividers, this.numberDividers);
    }

    private void setOperators(Set<? extends NumericObjectsOperator> sources, NumericObjectsOperator[] destination) {
        sources.stream()
                .filter(numberOperator -> Objects.nonNull(numberOperator.getSupportedType()))
                .forEach(operator -> destination[operator.getSupportedType().getIndex()] = operator);
    }
}
