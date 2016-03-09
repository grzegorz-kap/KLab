package interpreter.parsing.handlers;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.factory.operator.OperatorFactory;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.model.tokens.operators.OperatorAssociativity;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT;
import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.RIGHT_TO_LEFT;
import static java.util.Objects.nonNull;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OperatorParseHandler extends AbstractParseHandler {
    private OperatorFactory operatorFactory;

    @Override
    public void handle() {
    	preProcessUnaryPlusAndMinusOperators();
        OperatorToken o1 = operatorFactory.getOperator(getContextManager().tokenAt(0));
        if (Objects.isNull(o1)) {
            throw new RuntimeException();
        }
        while (!pCtxMgr.isStackEmpty()) {
            ParseToken parseToken = pCtxMgr.stackPeek();
            if (!(parseToken instanceof OperatorToken)) {
                break;
            }

            OperatorToken o2 = (OperatorToken) parseToken;
            int compereResult = o1.getPriority().compareTo(o2.getPriority());
            boolean operate = o1.getAssociativity() == LEFT_TO_RIGHT && compereResult <= 0 ||
                    o1.getAssociativity() == RIGHT_TO_LEFT && compereResult <= -1;

            if (!operate) {
                break;
            }

            if (":".equals(o1.getToken().getLexeme()) && ":".equals(o2.getToken().getLexeme())) {
                pCtxMgr.stackPop();
                o1 = operatorFactory.getOperator("$::", o2.getToken());
                break;
            }

            stackToExpression();
        }
        pCtxMgr.stackPush(o1);
        pCtxMgr.incrementTokenPosition(1);
    }
    
    private void preProcessUnaryPlusAndMinusOperators() {
        String token = pCtxMgr.tokenAt(0).getLexeme();
        boolean isUnary = false;
    	if("+".equals(token) || "-".equals(token)) {
    		TokenClass previousClass = previousTokenClass();
    		isUnary = previousClass == null || previousClass.isUnaryOpPrecursor();
    		if(!isUnary && TokenClass.OPERATOR.equals(previousClass)) {
                OperatorToken previous = operatorFactory.getOperator(pCtxMgr.tokenAt(-1));
                isUnary = previous.getArgumentsNumber() > 1 || previous.getAssociativity().equals(OperatorAssociativity.RIGHT_TO_LEFT);
    		}  		
    	}
    	if(isUnary) {
            pCtxMgr.tokenAt(0).setLexeme("$" + token);
        }
    }
     
    private TokenClass previousTokenClass() {
        Token token = pCtxMgr.tokenAt(-1);
        return nonNull(token) ? token.getTokenClass() : null;
    }

    @Override
    public void handleStackFinish() {
        stackToExpression();
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.OPERATOR;
    }

    private void stackToExpression() {
        OperatorToken operatorToken = (OperatorToken) pCtxMgr.stackPop();
        ExpressionNode<ParseToken> expressionNode = new ExpressionNode<>(operatorToken);
        expressionNode.addChildren(pCtxMgr.expressionPopArguments(operatorToken.getArgumentsNumber()));
        pCtxMgr.addExpression(expressionNode);
    }

    @Autowired
    public void setOperatorFactory(OperatorFactory operatorFactory) {
        this.operatorFactory = operatorFactory;
    }
}
