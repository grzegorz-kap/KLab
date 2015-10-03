package interpreter.translate.service;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionValue;
import interpreter.translate.handlers.TranslateHandler;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InstructionTranslatorService extends AbstractInstructionTranslator {

    @Autowired
    public InstructionTranslatorService(Set<TranslateHandler> translateHandlers) {
        super(translateHandlers);
    }

    @Override
    protected void translate() {
        Expression<ParseToken> parseTokenExpression = translateContext.getExpression();
        process(parseTokenExpression);
        addPrint();
    }

    private void translateExpressionValue(Expression<ParseToken> expression) {
        getTranslateHandler(expression.getValue().getParseClass()).handle(expression);
    }

    private void translateExpressionNode(Expression<ParseToken> expression) {
        expression.getChildren().forEach(this::process);
        translateExpressionValue(expression);
    }

    private void process(Expression<ParseToken> parseTokenExpression) {
        if (parseTokenExpression instanceof ExpressionValue) {
            translateExpressionValue(parseTokenExpression);
        } else {
            translateExpressionNode(parseTokenExpression);
        }
    }

    private void addPrint() {
        translateContextManager.addInstruction(new Instruction(InstructionCode.PRINT, 0));
    }
}
