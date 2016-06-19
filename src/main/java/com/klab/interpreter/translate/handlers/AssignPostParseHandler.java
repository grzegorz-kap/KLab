package com.klab.interpreter.translate.handlers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.parsing.model.CallToken;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.tokens.IdentifierToken;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorCode;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.model.ReverseStoreInstruction;
import com.klab.interpreter.translate.service.ExpressionTranslator;
import com.klab.interpreter.types.ModifyingIdentifierObject;
import com.klab.interpreter.types.TokenIdentifierObject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AssignPostParseHandler extends AbstractPostParseHandler {
    @Override
    public boolean canBeHandled(List<Expression<ParseToken>> expressions) {
        if (expressions.size() != 1) {
            return false;
        }
        Expression<ParseToken> expression = expressions.get(0);
        ParseToken parseToken = expression.getValue();
        return parseToken != null &&
                parseToken instanceof OperatorToken &&
                OperatorCode.ASSIGN.equals(((OperatorToken) parseToken).getOperatorCode());
    }

    @Override
    public boolean isInstructionCompletelyTranslated() {
        return true;
    }

    @Override
    public MacroInstruction handle(List<Expression<ParseToken>> expressions, ExpressionTranslator expressionTranslator) {
        Expression<ParseToken> expression = expressions.get(0);
        MacroInstruction code = new MacroInstruction();
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
            code.add(expressionTranslator.translate(expression.getChildren().get(1)));
            boolean print = expression.getProperty(Expression.PRINT_PROPERTY_KEY);
            Map<String, List<Instruction>> names = Maps.newHashMap();
            for (Expression<ParseToken> target : left.getChildren().get(0).getChildren()) {
                List<Instruction> instructions = names.computeIfAbsent(target.getValue().getLexeme(), key -> Lists.newArrayList());
                if (target.getValue().getParseClass().equals(ParseClass.IDENTIFIER)) {
                    TokenIdentifierObject id = new TokenIdentifierObject((IdentifierToken) target.getValue());
                    code.add(new Instruction(InstructionCode.PUSH, 0, id), address);
                    ReverseStoreInstruction instruction = new ReverseStoreInstruction(print);
                    code.add(instruction, address);
                    instructions.add(instruction);
                } else if (target.getValue().getParseClass().equals(ParseClass.CALL)) {
                    instructions.add(createModifyAssign(code, target, print, expressionTranslator));
                } else {
                    throw new RuntimeException(); //TODO runtime exception
                }
            }
            for (List<Instruction> instructions : names.values()) {
                instructions.subList(0, instructions.size() - 1).forEach(instruction -> instruction.setPrint(false));
            }
        } else if (left.getValue().getParseClass().equals(ParseClass.CALL)) {
            code.add(expressionTranslator.translate(expression.getChildren().get(1)));
            createModifyAssign(code, left, expression.getProperty(Expression.PRINT_PROPERTY_KEY), expressionTranslator);
        } else {
            code.add(expressionTranslator.translate(expression));
        }
        return code;
    }

    private Instruction createModifyAssign(MacroInstruction code, Expression<ParseToken> target, boolean print, ExpressionTranslator expressionTranslator) {
        if (target.getChildren().size() < 1 || target.getChildren().size() > 2) {
            throw new RuntimeException();
        }
        target.getChildren().forEach(e -> code.add(expressionTranslator.translate(e)));
        // TODO check if variable is defined
        CallToken var = (CallToken) target.getValue();
        CodeAddress address = target.getValue().getAddress();
        ModifyingIdentifierObject identifierObject = new ModifyingIdentifierObject(var.getVariableAddress(), var.getCallName());
        code.add(new Instruction(InstructionCode.PUSH, 0, identifierObject), address);
        InstructionCode instructionCode = target.getChildren().size() == 2 ? InstructionCode.MODIFY2 : InstructionCode.MODIFY1;
        Instruction instruction = new Instruction(instructionCode, 0);
        instruction.setPrint(print);
        code.add(instruction, address);
        return instruction;
    }


    @Override
    public void reset() {
    }
}
