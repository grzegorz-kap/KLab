package interpreter.translate.service;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionValue;
import interpreter.parsing.model.tokens.IdentifierToken;
import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import interpreter.service.functions.model.CallToken;
import interpreter.translate.exception.UnsupportedParseToken;
import interpreter.translate.handlers.TranslateHandler;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
import interpreter.translate.model.JumperInstruction;
import interpreter.types.ModifyingIdentifierObject;
import interpreter.types.TokenIdentifierObject;
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
        if (getAnsProperty()) {
            translateContextManager.addInstruction(new Instruction(InstructionCode.ANS, 1));
        }
        if (getPrintProperty()) {
            translateContextManager.addInstruction(new Instruction(InstructionCode.PRINT, 0));
        }
    }

    private void translateExpressionValue(Expression<ParseToken> expression) {
        TranslateHandler translateHandler = getTranslateHandler(expression.getValue().getParseClass());
        checkIfSupported(expression, translateHandler);
        translateHandler.handle(expression);
    }

    private void translateExpressionNode(Expression<ParseToken> expression) {
        if (checkIfOperator(OperatorCode.AND, expression.getValue())) {
            handleShortCircuitOperator(expression, InstructionCode.JMPFNP);
        } else if (checkIfOperator(OperatorCode.OR, expression.getValue())) {
            handleShortCircuitOperator(expression, InstructionCode.JMPTNP);
        } else if (checkIfOperator(OperatorCode.ASSIGN, expression.getValue())) {
            handleAssignOperator(expression);
        } else {
            expression.getChildren().forEach(this::process);
            translateExpressionValue(expression);
        }
    }

    private void handleAssignOperator(Expression<ParseToken> expression) {
        Expression<ParseToken> left = expression.getChildren().get(0);
        if (left.getValue().getParseClass().equals(ParseClass.MATRIX)) {
            if (left.getChildren().size() != 1) {  // only verse vector allowed // TODO column vector to
                throw new RuntimeException("Wrong assignment target");
            }
            process(expression.getChildren().get(1));
            for (Expression<ParseToken> target : left.getChildren().get(0).getChildren()) {
                if (target.getValue().getParseClass().equals(ParseClass.IDENTIFIER)) {
                    translateContextManager.addInstruction(new Instruction(InstructionCode.PUSH, 0, new TokenIdentifierObject((IdentifierToken) target.getValue())));
                    translateContextManager.addInstruction(new Instruction(InstructionCode.RSTORE, 0));
                } else if (target.getValue().getParseClass().equals(ParseClass.CALL)) {
                    createModifyAssign(target);
                } else {
                    throw new RuntimeException(); //TODO runtime exception
                }
            }
        } else if (left.getValue().getParseClass().equals(ParseClass.CALL)) {
            process(expression.getChildren().get(1));
            createModifyAssign(left);
        } else {
            expression.getChildren().forEach(this::process);
            translateExpressionValue(expression);
        }
    }

    private void createModifyAssign(Expression<ParseToken> target) {
        target.getChildren().forEach(this::process);
        if (target.getChildren().size() < 1 || target.getChildren().size() > 2) {
            throw new RuntimeException();
        }
        // TODO check if variable is defined
        CallToken var = (CallToken) target.getValue();
        translateContextManager.addInstruction(new Instruction(InstructionCode.PUSH, 0, new ModifyingIdentifierObject(var.getVariableAddress(), var.getCallName())));
        InstructionCode code = target.getChildren().size() == 2 ? InstructionCode.MODIFY2 : InstructionCode.MODIFY1;
        translateContextManager.addInstruction(new Instruction(code, 0));
    }

    private void handleShortCircuitOperator(Expression<ParseToken> expression, InstructionCode jmptnp) {
        process(expression.getChildren().get(0));
        translateContextManager.addInstruction(new Instruction(InstructionCode.LOGICAL, 0));
        JumperInstruction jmpt = new JumperInstruction(jmptnp, 0);
        translateContextManager.addInstruction(jmpt);
        process(expression.getChildren().get(1));
        translateExpressionValue(expression);
        jmpt.setJumpIndex(code.size() + translateContext.getMacroInstruction().size());
    }

    private boolean checkIfOperator(OperatorCode operatorCode, ParseToken parseToken) {
        if (parseToken instanceof OperatorToken) {
            OperatorToken operatorToken = (OperatorToken) parseToken;
            return operatorCode.equals(operatorToken.getOperatorCode());
        } else {
            return false;
        }
    }

    private void process(Expression<ParseToken> parseTokenExpression) {
        if (parseTokenExpression instanceof ExpressionValue) {
            translateExpressionValue(parseTokenExpression);
        } else {
            translateExpressionNode(parseTokenExpression);
        }
    }

    private boolean getAnsProperty() {
        return Optional
                .ofNullable(translateContext.getExpression().getProperty(Expression.ANS_PROPERTY_KEY, Boolean.class))
                .orElse(true);
    }

    private boolean getPrintProperty() {
        return Optional
                .ofNullable(translateContext.getExpression().getProperty(Expression.PRINT_PROPERTY_KEY, Boolean.class))
                .orElse(false);
    }

    private void checkIfSupported(Expression<ParseToken> expression, TranslateHandler translateHandler) {
        if (Objects.isNull(translateHandler)) {
            throw new UnsupportedParseToken(UNEXPECTED_TOKEN_MESSAGE, expression.getValue());
        }
    }
}
