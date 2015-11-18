package interpreter.parsing.handlers.helpers;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.types.NumericType;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.nonNull;

@Component
public class TypeResolver {

    public NumericType resolveNumeric(Collection<? extends Expression<ParseToken>> expressions) {
        return countAlreadyResolved(expressions) != expressions.size() ? null :
                expressions.parallelStream().max(this::comparePriority).map(Expression::getResolvedNumericType).orElse(null);
    }

    private int comparePriority(Expression<ParseToken> ex1, Expression<ParseToken> ex2) {
        return ex1.getResolvedNumericType().getPriority().compareTo(ex2.getResolvedNumericType().getPriority());
    }

    private long countAlreadyResolved(Collection<? extends Expression<ParseToken>> expressions) {
        return expressions.parallelStream().filter(ex -> nonNull(ex.getResolvedNumericType())).count();
    }
}
