package interpreter.translate.keyword;

import interpreter.execution.model.Code;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.model.InstructionCode;
import interpreter.translate.model.JumperInstruction;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.service.InstructionTranslator;

import java.util.List;

public abstract class AbstractPostParseHandler implements PostParseHandler {
    protected Code code;

    @Override
    public abstract boolean canBeHandled(List<Expression<ParseToken>> expressions);

    @Override
    public abstract boolean executionCanStart();

    @Override
    public abstract MacroInstruction handle(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator);

    @Override
    public void setCode(Code code) {
        this.code = code;
    }

    protected boolean isParseClass(List<Expression<ParseToken>> expressions, ParseClass parseClass, int index) {
        return expressions.get(index).getValue().getParseClass().equals(parseClass);
    }

    protected void setupNoPrintNoAns(List<Expression<ParseToken>> expressions, int index) {
        expressions.get(index).setProperty(Expression.PRINT_PROPERTY_KEY, false);
        expressions.get(index).setProperty(Expression.ANS_PROPERTY_KEY, false);
    }

    protected JumperInstruction createJmpInstruction() {
        JumperInstruction jumperInstruction = new JumperInstruction();
        jumperInstruction.setArgumentsNumber(0);
        jumperInstruction.setInstructionCode(InstructionCode.JMP);
        return jumperInstruction;
    }

    protected JumperInstruction createJumpOnFalse() {
        JumperInstruction jumperInstruction = new JumperInstruction();
        jumperInstruction.setArgumentsNumber(0);
        jumperInstruction.setInstructionCode(InstructionCode.JMPF);
        return jumperInstruction;
    }
}
