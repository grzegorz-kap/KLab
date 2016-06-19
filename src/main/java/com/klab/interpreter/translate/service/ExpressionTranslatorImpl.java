package com.klab.interpreter.translate.service;

import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.expression.ExpressionValue;
import com.klab.interpreter.parsing.model.tokens.IdentifierToken;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorCode;
import com.klab.interpreter.service.functions.model.CallToken;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.translate.model.JumperInstruction;
import com.klab.interpreter.translate.model.ReverseStoreInstruction;
import com.klab.interpreter.types.ModifyingIdentifierObject;
import com.klab.interpreter.types.TokenIdentifierObject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExpressionTranslatorImpl extends AbstractExpressionTranslator {
    @Override
    protected void translate() {
        Expression<ParseToken> parseTokenExpression = translateContext.getExpression();
        process(parseTokenExpression);
        if (getAnsProperty()) {
            manager.addInstruction(new Instruction(InstructionCode.ANS, 1), manager.previousCodeAddress());
        }
        if (getPrintProperty()) {
            manager.addInstruction(new Instruction(InstructionCode.PRINT, 0), manager.previousCodeAddress());
        }
    }

    private void process(Expression<ParseToken> parseTokenExpression) {
        if (parseTokenExpression instanceof ExpressionValue) {
            translateExpressionValue(parseTokenExpression);
        } else {
            translateExpressionNode(parseTokenExpression);
        }
    }

    private void translateExpressionValue(Expression<ParseToken> expression) {
        getTranslateHandler(expression.getValue().getParseClass()).handle(expression);
    }


    private void translateExpressionNode(Expression<ParseToken> expression) {
        if (checkIfOperator(OperatorCode.AND, expression.getValue())) {
            handleShortCircuitOperator(expression, InstructionCode.JUMP_IF_FALSE);
        } else if (checkIfOperator(OperatorCode.OR, expression.getValue())) {
            handleShortCircuitOperator(expression, InstructionCode.JUMP_IF_TRUE);
        } else if (checkIfOperator(OperatorCode.ASSIGN, expression.getValue())) {
            handleAssignOperator(expression);
        } else {
            for (Expression<ParseToken> child : expression.getChildren()) {
                process(child);
            }
            translateExpressionValue(expression);
        }
    }

    private void handleAssignOperator(Expression<ParseToken> expression) {
        if (expression.getParent() != null) {
            throw new RuntimeException();
        }
        expression.setProperty(Expression.ANS_PROPERTY_KEY, false);
        CodeAddress address = expression.getValue().getAddress();
        Expression<ParseToken> left = expression.getChildren().get(0);
        if (left.getValue().getParseClass().equals(ParseClass.MATRIX)) {
            if (left.getChildren().size() != 1) {  // only verse vector allowed // TODO column vector to
                throw new RuntimeException("Wrong assignment target");
            }
            process(expression.getChildren().get(1));
            boolean print = expression.getProperty(Expression.PRINT_PROPERTY_KEY, Boolean.class);
            for (Expression<ParseToken> target : left.getChildren().get(0).getChildren()) {
                if (target.getValue().getParseClass().equals(ParseClass.IDENTIFIER)) {
                    TokenIdentifierObject id = new TokenIdentifierObject((IdentifierToken) target.getValue());
                    manager.addInstruction(new Instruction(InstructionCode.PUSH, 0, id), address);
                    manager.addInstruction(new ReverseStoreInstruction(print), address);
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
        if (target.getChildren().size() < 1 || target.getChildren().size() > 2) {
            throw new RuntimeException();
        }
        target.getChildren().forEach(this::process);
        // TODO check if variable is defined
        CallToken var = (CallToken) target.getValue();
        CodeAddress address = target.getValue().getAddress();
        ModifyingIdentifierObject identifierObject = new ModifyingIdentifierObject(var.getVariableAddress(), var.getCallName());
        manager.addInstruction(new Instruction(InstructionCode.PUSH, 0, identifierObject), address);
        InstructionCode code = target.getChildren().size() == 2 ? InstructionCode.MODIFY2 : InstructionCode.MODIFY1;
        manager.addInstruction(new Instruction(code, 0), address);
    }

    private void handleShortCircuitOperator(Expression<ParseToken> expression, InstructionCode jmptnp) {
        process(expression.getChildren().get(0));
        manager.addInstruction(new Instruction(InstructionCode.LOGICAL, 0), expression.getValue().getAddress());
        JumperInstruction jmpt = new JumperInstruction(jmptnp, 0);
        manager.addInstruction(jmpt, expression.getValue().getAddress());
        process(expression.getChildren().get(1));
        translateExpressionValue(expression);
        jmpt.setJumpIndex(code.size() + translateContext.getMacroInstruction().size());
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
}
