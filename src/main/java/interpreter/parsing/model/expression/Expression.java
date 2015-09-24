package interpreter.parsing.model.expression;

import java.util.Collection;
import java.util.List;

public interface Expression<T> {
    Expression<T> getParent();

    void setParent(Expression<T> expression);

    void addChild(Expression<T> expression);

    void addChildren(Collection<? extends Expression<T>> expressions);

    List<Expression<T>> getChildren();

    T getValue();

    void setValue(T value);

    int getChildrenCount();
}
