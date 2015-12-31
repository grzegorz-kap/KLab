package interpreter.parsing.model.expression;

import interpreter.types.NumericType;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface Expression<T> {
    String PRINT_PROPERTY_KEY = "print";
    String ANS_PROPERTY_KEY = "ans";

    Expression<T> getParent();

    void setParent(Expression<T> expression);

    void addChildren(Collection<? extends Expression<T>> expressions);

    List<Expression<T>> getChildren();

    T getValue();

    void setValue(T value);

    int getChildrenCount();

    void setProperty(String key, Object property);

    <P> P getProperty(String key, Class<P> clazz);

    NumericType getResolvedNumericType();

    void setResolvedNumericType(NumericType resolvedNumericType);

    void visitEach(Consumer<Expression<? super T>> consumer);
}