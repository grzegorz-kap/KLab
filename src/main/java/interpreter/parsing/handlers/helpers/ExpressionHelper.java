package interpreter.parsing.handlers.helpers;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.service.ParseContextManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


// TODO remove bean, add static
@Component
public class ExpressionHelper {

    public List<Expression<ParseToken>> popUntilParseClass(ParseContextManager parseContextManager, Predicate<ParseClass> predicate) {
        Integer foundIndex = findLastIndex(parseContextManager, predicate);
        checkCorrectIndexFound(foundIndex);
        return parseContextManager.expressionPopArguments(parseContextManager.expressionSize() - foundIndex - 1);
    }

    private void checkCorrectIndexFound(Integer matrixStartIndex) {
        if (Objects.isNull(matrixStartIndex)) {
            throw new RuntimeException();
        }
    }

    private Integer findLastIndex(ParseContextManager parseContextManager, Predicate<ParseClass> predicate) {
        return parseContextManager.findLast(
                expression -> predicate.test(expression.getValue().getParseClass())
        );
    }
}
