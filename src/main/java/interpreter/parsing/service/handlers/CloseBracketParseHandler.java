package interpreter.parsing.service.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionNode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CloseBracketParseHandler extends AbstractParseHandler implements ParseHandler {

    @PostConstruct
    private void init() {
        supportedTokenClass = TokenClass.CLOSE_BRACKET;
    }

    @Override
    public void handle() {
        moveStackToExpression();
        reduceExpression();
        parseContextManager.incrementTokenPosition(1);
    }

    private void reduceExpression() {
        ExpressionNode<ParseToken> matrixNode = new ExpressionNode<>();
        Integer matrixStartIndex = findMatrixStartIndex();
        checkCorrectIndexFound(matrixStartIndex);
        int expressionNodesToPop = parseContextManager.expressionSize() - matrixStartIndex - 1;
        matrixNode.addChildren(parseContextManager.popExpressionArguments(expressionNodesToPop));
        matrixNode.setValue(parseContextManager.popExpression().getValue());
        parseContextManager.addExpression(matrixNode);
    }

    private void checkCorrectIndexFound(Integer matrixStartIndex) {
        if (Objects.isNull(matrixStartIndex)) {
            throw new RuntimeException();
        }
    }

    private void moveStackToExpression() {
        while (!parseContextManager.isStackEmpty() && !isMatrixStartAtStackPeek()) {
            parseContextManager.getParseHandler(parseContextManager.stackPeekClass()).handleStackFinish();
        }
        if (parseContextManager.isStackEmpty()) {
            throw new RuntimeException();
        }
        parseContextManager.stackPop();
    }

    private boolean isMatrixStartAtStackPeek() {
        return parseContextManager.stackPeekClass().equals(TokenClass.OPEN_BRACKET);
    }

    private Integer findMatrixStartIndex() {
        return parseContextManager.findLast(
                expression -> expression.getValue().getTokenClass().equals(TokenClass.OPEN_BRACKET)
        );
    }
}
