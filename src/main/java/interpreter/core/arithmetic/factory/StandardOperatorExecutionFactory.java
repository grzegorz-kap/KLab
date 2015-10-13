package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.*;
import interpreter.parsing.model.NumericType;
import org.springframework.stereotype.Service;

@Service
public class StandardOperatorExecutionFactory extends AbstractStandardOperatorExecutionFactory {

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

    @Override
    public NumericObjectsComparator getComporator(NumericType numericType) {
        return objectsComparators[numericType.getIndex()];
    }
}
