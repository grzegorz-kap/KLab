package com.klab.interpreter.parsing.handlers.matrix;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.handlers.helpers.ExpressionHelper;
import com.klab.interpreter.parsing.handlers.helpers.StackHelper;
import com.klab.interpreter.parsing.handlers.helpers.TypeResolver;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.expression.ExpressionNode;
import com.klab.interpreter.parsing.model.tokens.VerseToken;
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
    public TokenClass supportedTokenClass() {
        return null;
    }

    @Override
    public void handle() {
        handleAction();
        parseContextManager.incrementTokenPosition(1);
    }

    public void handleAction() {
        stackHelper.stackToExpressionUntilParseClass(parseContextManager, ParseClass.MATRIX_START);
        List<Expression<ParseToken>> expressions = expressionHelper.popUntilParseClass(parseContextManager, this::popExpressionPredicate);
        parseContextManager.addExpression(createExpressionNode(expressions));
    }

    private ExpressionNode<ParseToken> createExpressionNode(List<Expression<ParseToken>> expressions) {
        parseContextManager.tokenAt(0).setLexeme(";");
        VerseToken verseToken = new VerseToken(parseContextManager.tokenAt(0));
        ExpressionNode<ParseToken> expressionNode = new ExpressionNode<>(verseToken);
        expressionNode.setResolvedNumericType(typeResolver.resolveNumeric(expressions));
        expressionNode.addChildren(expressions);
        return expressionNode;
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
