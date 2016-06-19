package com.klab.interpreter.parsing.model.expression;

import java.util.Collection;
import java.util.List;

public class ExpressionValue<T> extends AbstractExpression<T> implements Expression<T> {
    public ExpressionValue(T value) {
        setValue(value);
    }

    @Override
    public int getChildrenCount() {
        return 0;
    }

    @Override
    public void addChildren(Collection<? extends Expression<T>> expressions) {
        throw new UnsupportedOperationException("addChildren");
    }

    @Override
    public List<Expression<T>> getChildren() {
        throw new UnsupportedOperationException("addChildren");
    }
}
