package interpreter.parsing.handlers.matrix;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.handlers.helpers.ExpressionHelper;
import interpreter.parsing.handlers.helpers.StackHelper;
import interpreter.parsing.handlers.helpers.TypeResolver;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.model.tokens.VerseToken;
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
    private TypeResolver typeResolver;

    @Override
    public TokenClass getSupportedTokenClass() {
        return null;
    }

    @Override
    public void handle() {
        handleAction();
        pCtxMgr.incrementTokenPosition(1);
    }

    public void handleAction() {
        stackHelper.stackToExpressionUntilParseClass(pCtxMgr, ParseClass.MATRIX_START);
        List<Expression<ParseToken>> expressions = popExpressions();
        VerseToken verseToken = createVerseToken();
        pCtxMgr.addExpression(createExpressionNode(expressions, verseToken));
    }

    private ExpressionNode<ParseToken> createExpressionNode(List<Expression<ParseToken>> expressions, VerseToken verseToken) {
        ExpressionNode<ParseToken> expressionNode = new ExpressionNode<>(verseToken);
        expressionNode.setResolvedNumericType(typeResolver.resolveNumeric(expressions));
        expressionNode.addChildren(expressions);
        return expressionNode;
    }

    private VerseToken createVerseToken() {
        Token token = pCtxMgr.tokenAt(0);
        token.setLexeme(";");
        return new VerseToken(token);
    }

    private List<Expression<ParseToken>> popExpressions() {
        return expressionHelper.popUntilParseClass(pCtxMgr, this::popExpressionPredicate);
    }

    private boolean popExpressionPredicate(ParseClass parseClass) {
        return parseClass.equals(ParseClass.MATRIX_START) || parseClass.equals(ParseClass.MATRIX_VERSE);
    }

    @Autowired
    public void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }

    @Autowired
    public void setExpressionHelper(ExpressionHelper expressionHelper) {
        this.expressionHelper = expressionHelper;
    }

    @Autowired
    public void setTypeResolver(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }
}
