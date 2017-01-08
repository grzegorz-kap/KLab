package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.translate.model.JumperInstruction;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.service.ExpressionTranslator;

import java.util.List;
import java.util.Map;

public abstract class AbstractPostParseHandler implements PostParseHandler {
    protected Code code;


    @Override
    public boolean canBeHandled(List<Expression<ParseToken>> expressions) {
        return getHandlersMap().keySet().stream().anyMatch(p -> p.test(expressions));
    }

    protected abstract Map<ExpressionPredicate, HandleAction> getHandlersMap();

    @Override
    public abstract boolean isInstructionCompletelyTranslated();

    @Override
    public MacroInstruction handle(List<Expression<ParseToken>> expressions, ExpressionTranslator expressionTranslator) {
        MacroInstruction instruction = new MacroInstruction();
        for (Map.Entry<ExpressionPredicate, HandleAction> entry : getHandlersMap().entrySet()) {
            if (entry.getKey().test(expressions)) {
                instruction.add(entry.getValue().handle(expressions, expressionTranslator));
            }
        }
        return instruction;
    }

    @Override
    public void setCode(Code code) {
        this.code = code;
    }

    boolean isParseClass(List<Expression<ParseToken>> expressions, ParseClass parseClass, int index) {
        return expressions.get(index).getValue().getParseClass().equals(parseClass);
    }

    void setupNoPrintNoAns(List<Expression<ParseToken>> expressions, int index) {
        expressions.get(index).setProperty(Expression.PRINT_PROPERTY_KEY, false);
        expressions.get(index).setProperty(Expression.ANS_PROPERTY_KEY, false);
    }

    JumperInstruction createJmpInstruction() {
        JumperInstruction jumperInstruction = new JumperInstruction();
        jumperInstruction.setArgumentsNumber(0);
        jumperInstruction.setInstructionCode(InstructionCode.JMP);
        return jumperInstruction;
    }

    JumperInstruction createJumpOnFalse() {
        JumperInstruction jumperInstruction = new JumperInstruction();
        jumperInstruction.setArgumentsNumber(0);
        jumperInstruction.setInstructionCode(InstructionCode.JMPF);
        return jumperInstruction;
    }
}
