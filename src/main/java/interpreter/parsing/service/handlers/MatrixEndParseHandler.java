package interpreter.parsing.service.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.service.handlers.helpers.StackHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixEndParseHandler extends AbstractParseHandler implements ParseHandler {

    private StackHelper stackHelper;

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
        if (!stackHelper.stackToExpressionUntilTokenClass(parseContextManager, TokenClass.OPEN_BRACKET)) {
            throw new RuntimeException();
        }
        parseContextManager.stackPop();
    }

    private Integer findMatrixStartIndex() {
        return parseContextManager.findLast(
                expression -> expression.getValue().getTokenClass().equals(TokenClass.OPEN_BRACKET)
        );
    }

    @Autowired
    public void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }
}
