package interpreter.parsing.model.expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractExpression<T> implements Expression<T> {
    protected Expression<T> parent;
    protected T value;
    private HashMap<String, Object> properties = new HashMap<>();

    @Override
    public Expression<T> getParent() {
        return parent;
    }

    @Override
    public void setParent(Expression<T> parent) {
        this.parent = parent;
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
}
