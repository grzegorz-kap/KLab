package interpreter.parsing.service.handlers.matrix;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.model.tokens.VerseToken;
import interpreter.parsing.service.handlers.AbstractParseHandler;
import interpreter.parsing.service.handlers.helpers.ExpressionHelper;
import interpreter.parsing.service.handlers.helpers.StackHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixNewRowHandler extends AbstractParseHandler {

    private StackHelper stackHelper;
    private ExpressionHelper expressionHelper;

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.SEMICOLON;
    }

    @Override
    public void handle() {
        stackHelper.stackToExpressionUntilTokenClass(parseContextManager, ParseClass.MATRIX_START);
        List<Expression<ParseToken>> expressions = popExpressions();
        VerseToken verseToken = createVerseToken();
        parseContextManager.addExpression(createExpressionNode(expressions, verseToken));
        parseContextManager.incrementTokenPosition(1);
    }

    private ExpressionNode<ParseToken> createExpressionNode(List<Expression<ParseToken>> expressions, VerseToken verseToken) {
        ExpressionNode<ParseToken> expressionNode = new ExpressionNode<>(verseToken);
        expressionNode.addChildren(expressions);
        return expressionNode;
    }

    private VerseToken createVerseToken() {
        VerseToken verseToken = new VerseToken(parseContextManager.tokenAt(0));
        verseToken.setParseClass(ParseClass.MATRIX_VERSE);
        return verseToken;
    }

    private List<Expression<ParseToken>> popExpressions() {
        return expressionHelper.popUntilParseClass(parseContextManager,
                (parseClass -> parseClass.equals(ParseClass.MATRIX_START) || parseClass.equals(ParseClass.MATRIX_VERSE)));
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
