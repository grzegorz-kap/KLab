package com.klab.interpreter.parsing.model.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class ExpressionNode<T> extends AbstractExpression<T> implements Expression<T> {
    private List<Expression<T>> children = new ArrayList<>();

    public ExpressionNode(T value) {
        this.value = value;
    }

    @Override
    public void visitEach(Consumer<Expression<? super T>> consumer) {
        super.visitEach(consumer);
        children.forEach(child -> child.visitEach(consumer));
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

    @Override
    public int getChildrenCount() {
        return children.size();
    }
}
