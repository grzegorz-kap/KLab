package com.klab.interpreter.parsing.model.expression;

import com.klab.interpreter.types.NumericType;

import java.util.HashMap;
import java.util.function.Consumer;

abstract class AbstractExpression<T> implements Expression<T> {
    private Expression<T> parent;
    private T value;
    private NumericType resolvedNumericType;
    private HashMap<String, Object> properties = new HashMap<>();

    @Override
    public void visitEach(Consumer<Expression<? super T>> consumer) {
        consumer.accept(this);
    }

    @Override
    public Expression<T> getParent() {
        return parent;
    }

    @Override
    public void setParent(Expression<T> parent) {
        this.parent = parent;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public <P> P getProperty(String key, Class<P> clazz) {
        return clazz.cast(properties.get(key));
    }

    @Override
    public NumericType getResolvedNumericType() {
        return resolvedNumericType;
    }

    @Override
    public void setResolvedNumericType(NumericType resolvedNumericType) {
        this.resolvedNumericType = resolvedNumericType;
    }
}
