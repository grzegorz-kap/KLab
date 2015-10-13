package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.*;
import interpreter.parsing.model.NumericType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;

public abstract class AbstractStandardOperatorExecutionFactory implements OperatorExecutionFactory {

    protected static final int NUMBER_TYPES_COUNT = NumericType.values().length;

    protected NumericObjectsAdder[] numericObjectsAdders = new NumericObjectsAdder[NUMBER_TYPES_COUNT];
    protected NumericObjectsSubtractor[] numericObjectsSubtractors = new NumericObjectsSubtractor[NUMBER_TYPES_COUNT];
    protected NumericObjectsMultiplicator[] numberMultiplicators = new NumericObjectsMultiplicator[NUMBER_TYPES_COUNT];
    protected NumericObjectsDivider[] numberDividers = new NumericObjectsDivider[NUMBER_TYPES_COUNT];
    protected NumericObjectsComparator[] objectsComparators = new NumericObjectsComparator[NUMBER_TYPES_COUNT];

    @Autowired
    private void setNumericObjectsAdders(Set<NumericObjectsAdder> numericObjectsAdders) {
        setOperators(numericObjectsAdders, this.numericObjectsAdders);
    }

    @Autowired
    private void setNumericObjectsSubtractors(Set<NumericObjectsSubtractor> numericObjectsSubtractors) {
        setOperators(numericObjectsSubtractors, this.numericObjectsSubtractors);
    }

    @Autowired
    private void setNumberMultiplicators(Set<NumericObjectsMultiplicator> numberMultiplicators) {
        setOperators(numberMultiplicators, this.numberMultiplicators);
    }

    @Autowired
    private void setNumberDividers(Set<NumericObjectsDivider> numberDividers) {
        setOperators(numberDividers, this.numberDividers);
    }

    @Autowired
    private void setObjectsComparators(Set<NumericObjectsComparator> numericObjectsComparators) {
        setOperators(numericObjectsComparators, this.objectsComparators);
    }

    private void setOperators(Set<? extends NumericObjectsOperator> sources, NumericObjectsOperator[] destination) {
        sources.stream()
                .filter(numberOperator -> Objects.nonNull(numberOperator.getSupportedType()))
                .forEach(operator -> destination[operator.getSupportedType().getIndex()] = operator);
    }
}
