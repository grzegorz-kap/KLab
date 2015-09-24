package interpreter.parsing.service.handlers.matrix;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.service.handlers.AbstractParseHandler;
import interpreter.parsing.service.handlers.helpers.ExpressionHelper;
import interpreter.parsing.service.handlers.helpers.StackHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static interpreter.parsing.model.ParseClass.MATRIX_START;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixEndParseHandler extends AbstractParseHandler {

    private StackHelper stackHelper;
    private ExpressionHelper expressionHelper;

    @Override
    public void handle() {
        moveStackToExpression();
        reduceExpression();
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.CLOSE_BRACKET;
    }

    private void reduceExpression() {
        ExpressionNode<ParseToken> matrixNode = new ExpressionNode<>();
        matrixNode.addChildren(expressionHelper.popUntilParseClass(parseContextManager, parseClass -> parseClass.equals(MATRIX_START)));
        matrixNode.setValue(parseContextManager.popExpression().getValue());
        matrixNode.getValue().setParseClass(ParseClass.MATRIX);
        parseContextManager.addExpression(matrixNode);
    }

    private void moveStackToExpression() {
        if (!stackHelper.stackToExpressionUntilTokenClass(parseContextManager, MATRIX_START)) {
            throw new RuntimeException();
        }
        parseContextManager.stackPop();
    }

    @Autowired
    public void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }

    @Autowired
    public void setExpressionHelper(ExpressionHelper expressionHelper) {
        this.expressionHelper = expressionHelper;
    }
}
