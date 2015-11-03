package interpreter.parsing.model.expression;

import interpreter.types.NumericType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractExpression<T> implements Expression<T> {
    protected Expression<T> parent;
    protected T value;
    protected NumericType resolvedNumericType;
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
    public void addChildren(Collection<? extends Expression<T>> expressions) {
        throw new UnsupportedOperationException("addChildren");
    }

    @Override
    public List<Expression<T>> getChildren() {
        throw new RuntimeException();
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
