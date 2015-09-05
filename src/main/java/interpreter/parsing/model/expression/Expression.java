package interpreter.parsing.model.expression;

import java.util.Collection;

public interface Expression<T> {
    Expression<T> getParent();

    void setParent(Expression<T> expression);

    void addChild(Expression<T> expression);

    void addChildren(Collection<? extends Expression<T>> expressions);

    T getValue();

    void setValue(T value);
}
