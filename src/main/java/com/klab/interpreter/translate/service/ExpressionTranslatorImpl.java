package com.klab.interpreter.translate.service;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.expression.ExpressionValue;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorCode;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken;
import com.klab.interpreter.translate.handlers.TranslateHandler;
import com.klab.interpreter.translate.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExpressionTranslatorImpl implements ExpressionTranslator {
    private TranslateContextManager manager = new TranslateContextManager();
    private TranslateContext translateContext;
    private Code code;
    private TranslateHandler[] handlers;

    @Override
    public MacroInstruction translate(Expression<ParseToken> parseTokenExpression) {
        translateContext = new TranslateContext(parseTokenExpression);
        manager.setTranslateContext(translateContext);
        process(parseTokenExpression);
        if (getAnsProperty()) {
            manager.addInstruction(new Instruction(InstructionCode.ANS, 1), manager.previousCodeAddress());
        }
        if (getPrintProperty()) {
            manager.addInstruction(new Instruction(InstructionCode.PRINT, 0), manager.previousCodeAddress());
        }
        return translateContext.getMacroInstruction();
    }

    @Override
    public void setupCode(Code code) {
        this.code = code;
    }

    private void process(Expression<ParseToken> parseTokenExpression) {
        if (parseTokenExpression instanceof ExpressionValue) {
            translateExpressionValue(parseTokenExpression);
        } else {
            translateExpressionNode(parseTokenExpression);
        }
    }

    private void translateExpressionValue(Expression<ParseToken> expression) {
        ParseClass parseClass = expression.getValue().getParseClass();
        TranslateHandler handler = handlers[parseClass.getIndex()];
        handler.handle(expression);
    }

    private void translateExpressionNode(Expression<ParseToken> expression) {
        if (checkIfOperator(OperatorCode.AND, expression.getValue())) {
            handleShortCircuitOperator(expression, InstructionCode.JUMP_IF_FALSE);
        } else if (checkIfOperator(OperatorCode.OR, expression.getValue())) {
            handleShortCircuitOperator(expression, InstructionCode.JUMP_IF_TRUE);
        } else {
            expression.getChildren().forEach(this::process);
            translateExpressionValue(expression);
        }
    }

    private boolean checkIfOperator(OperatorCode operatorCode, ParseToken parseToken) {
        return parseToken instanceof OperatorToken &&
                operatorCode.equals(((OperatorToken) parseToken).getOperatorCode());
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

    @Autowired
    public void setHandlers(TranslateHandler[] handlers) {
        this.handlers = new TranslateHandler[ParseClass.values().length];
        for (TranslateHandler translateHandler : handlers) {
            translateHandler.setTranslateContextManager(manager);
            this.handlers[translateHandler.supportedParseClass().getIndex()] = translateHandler;
        }
    }
}
