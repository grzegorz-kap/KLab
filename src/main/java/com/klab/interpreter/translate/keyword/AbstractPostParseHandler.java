package com.klab.interpreter.translate.keyword;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.translate.model.JumperInstruction;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.service.ExpressionTranslator;

import java.util.List;

public abstract class AbstractPostParseHandler implements PostParseHandler {
    protected Code code;

    @Override
    public abstract boolean canBeHandled(List<Expression<ParseToken>> expressions);

    @Override
    public abstract boolean isInstructionCompletelyTranslated();

    @Override
    public abstract MacroInstruction handle(List<Expression<ParseToken>> expressions, ExpressionTranslator expressionTranslator);

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
