package interpreter.parsing.handlers

import interpreter.parsing.factory.operator.OperatorFactory
import interpreter.parsing.model.ParseToken
import interpreter.parsing.model.expression.Expression
import interpreter.parsing.model.expression.ExpressionNode
import interpreter.parsing.service.ParseContextManager
import spock.lang.Specification

import static com.natpryce.makeiteasy.MakeItEasy.*
import static interpreter.lexer.makers.TokenMaker.*
import static interpreter.lexer.model.TokenClass.OPERATOR
import static interpreter.parsing.makers.OperatorTokenMaker.saveOperatorToken

class OperatorHandlerTest extends Specification {

    def operatorHandler = new OperatorParseHandler();
    def tokens;
    def index = 0;
    Deque<ParseToken> stack;
    List<Expression<ParseToken>> expressionList;

    def setup() {
        operatorHandler.contextManager = Mock(ParseContextManager)
        operatorHandler.setOperatorFactory(new OperatorFactory());
        stack = new ArrayDeque<>();
        expressionList = new ArrayList<>();
        tokens = [
                make(a(saveToken, with(lexame, "+"), with(tokenClass, OPERATOR))),
                make(a(saveToken, with(lexame, "*"), with(tokenClass, OPERATOR))),
                make(a(saveToken, with(lexame, "-"), with(tokenClass, OPERATOR)))
        ]
        index = 0;
    }

    def "Testing operators precedence"() {
        when:
        3.times {
            operatorHandler.handle()
        }
        then:
        3 * operatorHandler.contextManager.tokenAt(0) >> { return tokens[index] }
        5 * operatorHandler.contextManager.isStackEmpty() >> { stack.isEmpty() }
        3 * operatorHandler.contextManager.stackPush(_) >> { arguments -> stack.push(arguments[0]) }
        3 * operatorHandler.contextManager.incrementTokenPosition(1) >> { index++ }
        3 * operatorHandler.contextManager.stackPeek() >> { return stack.peek() }
        2 * operatorHandler.contextManager.stackPop() >> { return stack.pop() }
        2 * operatorHandler.contextManager.expressionPopArguments(2) >> []
        2 * operatorHandler.contextManager.addExpression(_) >> { arguments -> expressionList.add(arguments[0]) }
        stack.size() == 1
        expressionList.size() == 2
        tokens[2] == stack.peek().token
        expressionList[0].getValue().token == tokens[1]
        expressionList[1].getValue().token == tokens[0]
    }

    def "Testing stack finish method"() {
        given:
        def parseToken = make a(saveOperatorToken)
        stack.push(parseToken)
        when:
        operatorHandler.handleStackFinish()
        then:
        1 * operatorHandler.contextManager.stackPop() >> { return stack.pop() }
        1 * operatorHandler.contextManager.expressionPopArguments(2) >> []
        1 * operatorHandler.contextManager.addExpression(_ as ExpressionNode<ParseToken>) >> {}
    }
}
