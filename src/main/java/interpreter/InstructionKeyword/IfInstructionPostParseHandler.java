package interpreter.InstructionKeyword;

import interpreter.InstructionKeyword.model.IfInstructionContext;
import interpreter.execution.model.Code;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.model.instruction.InstructionCode;
import interpreter.translate.model.instruction.JumperInstruction;
import interpreter.translate.service.InstructionTranslator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IfInstructionPostParseHandler implements PostParseHandler {

    private IfInstructionContext ifInstructionContext = new IfInstructionContext();
    private Code code;

    @Override
    public boolean canBeHandled(List<Expression<ParseToken>> expressions) {
        return isIfStart(expressions) || isIfEnd(expressions);
    }

    @Override
    public boolean executionCanStart() {
        return ifInstructionContext.size() == 0;
    }

    @Override
    public MacroInstruction handle(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator) {
        if (isIfStart(expressions)) {
            return handleIFStart(expressions, instructionTranslator);
        }
        if (isIfEnd(expressions)) {
            return handleIfEnd(expressions, instructionTranslator);
        }
        return new MacroInstruction();
    }

    @Override
    public void setCode(Code code) {
        this.code = code;
    }

    private MacroInstruction handleIfEnd(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator) {
        setupJumpOnFalse();
        int addressToJump = code.size();
        ifInstructionContext.forEachEndIfJumper(jumperInstruction -> jumperInstruction.setJumpIndex(addressToJump));
        expressions.get(0).setProperty(Expression.PRINT_PROPERTY_KEY, false);
        ifInstructionContext.removeLastIf();
        return new MacroInstruction();
    }

    private MacroInstruction handleIFStart(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator) {
        ifInstructionContext.addIf();
        expressions.get(1).setProperty(Expression.PRINT_PROPERTY_KEY, false);
        MacroInstruction macroInstruction = instructionTranslator.translate(expressions.get(1));
        JumperInstruction jumperInstruction = new JumperInstruction();
        jumperInstruction.setArgumentsNumber(0);
        jumperInstruction.setInstructionCode(InstructionCode.JMPF);
        macroInstruction.add(jumperInstruction);
        ifInstructionContext.setJumpOnFalse(jumperInstruction);
        return macroInstruction;
    }

    private boolean isIfEnd(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 1 && isParseClass(expressions, ParseClass.END_IF);
    }

    private boolean isIfStart(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 2 && isParseClass(expressions, ParseClass.IF);
    }

    private boolean isParseClass(List<Expression<ParseToken>> expressions, ParseClass parseClass) {
        return expressions.get(0).getValue().getParseClass().equals(parseClass);
    }

    private void setupJumpOnFalse() {
        int addressToJump = code.size();
        JumperInstruction jumpOnFalse = ifInstructionContext.getJumpOnFalse();
        if(Objects.nonNull(jumpOnFalse)) {
            jumpOnFalse.setJumpIndex(addressToJump);
            ifInstructionContext.setJumpOnFalse(null);
        }
    }
}
