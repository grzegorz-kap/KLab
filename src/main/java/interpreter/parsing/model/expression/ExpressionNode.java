package interpreter.parsing.model.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExpressionNode<T> extends AbstractExpression<T> implements Expression<T> {

    private List<Expression<T>> children = new ArrayList<>();

    public ExpressionNode() {
    }

    public ExpressionNode(T value) {
        this.value = value;
    }

    @Override
    public void addChild(Expression<T> expression) {
        children.add(expression);
        expression.setParent(this);
    }

    @Override
    public void addChildren(Collection<? extends Expression<T>> expressions) {
        children.addAll(expressions);
        expressions.forEach(expression -> expression.setParent(this));
    }

    @Override
    public List<Expression<T>> getChildren() {
        return children;
    }
}
