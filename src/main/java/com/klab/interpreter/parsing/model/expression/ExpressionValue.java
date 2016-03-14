package com.klab.interpreter.parsing.model.expression;

public class ExpressionValue<T> extends AbstractExpression<T> implements Expression<T> {
    public ExpressionValue(T value) {
        this.value = value;
    }

    @Override
    public int getChildrenCount() {
        return 0;
    }
}
