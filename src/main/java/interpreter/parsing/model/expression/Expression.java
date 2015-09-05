package interpreter.parsing.model.expression;

import java.util.Collection;

public interface Expression<T> {
    void setParent(Expression<T> expression);

    Expression<T> getParent();

    void addChild(Expression<T> expression);

    void addChildren(Collection<? extends Expression<T>> expressions);

    T getValue();

    void setValue(T value);
}
