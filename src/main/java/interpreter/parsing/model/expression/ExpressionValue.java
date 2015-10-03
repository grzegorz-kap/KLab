package interpreter.parsing.model.expression;

public class ExpressionValue<T> extends AbstractExpression<T> implements Expression<T> {
    @Override
    public int getChildrenCount() {
        return 0;
    }
}
