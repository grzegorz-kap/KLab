package interpreter.parsing.model.expression;

import java.util.Collection;

public abstract class AbstractExpression<T> implements Expression<T> {
    protected Expression<T> parent;
    protected T value;

    @Override
    public Expression<T> getParent() {
        return parent;
    }

    @Override
    public void addChild(Expression<T> expression) {
        throw new RuntimeException("addChild");
    }

    @Override
    public void addChildren(Collection<? extends Expression<T>> expressions) {
        throw new RuntimeException("addChildren");
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
}
