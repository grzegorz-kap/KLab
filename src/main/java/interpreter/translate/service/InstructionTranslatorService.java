package interpreter.translate.service;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionValue;
import interpreter.translate.exception.UnsupportedParseToken;
import interpreter.translate.handlers.TranslateHandler;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InstructionTranslatorService extends AbstractInstructionTranslator {

    public static final String UNEXPECTED_TOKEN_MESSAGE = "Unexpected token";

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
        TranslateHandler translateHandler = getTranslateHandler(expression.getValue().getParseClass());
        checkIfSupported(expression, translateHandler);
        translateHandler.handle(expression);
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
        if (getPrintProperty()) {
            translateContextManager.addInstruction(new Instruction(InstructionCode.PRINT, 0));
        }
    }

    private boolean getPrintProperty() {
        return Optional.ofNullable(translateContext.getExpression().getProperty(
                Expression.PRINT_PROPERTY_KEY, Boolean.class)).orElse(false);
    }

    private void checkIfSupported(Expression<ParseToken> expression, TranslateHandler translateHandler) {
        if (Objects.isNull(translateHandler)) {
            throw new UnsupportedParseToken(UNEXPECTED_TOKEN_MESSAGE, expression.getValue());
        }
    }
}
