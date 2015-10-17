package interpreter.core.postparse;

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

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IfInstructionPostParseHandler implements PostParseHandler {

    @Override
    public boolean canBeHandled(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 2 && expressions.get(0).getValue().getParseClass().equals(ParseClass.IF);
    }

    @Override
    public MacroInstruction handle(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator) {
        expressions.get(1).setProperty(Expression.PRINT_PROPERTY_KEY, false);
        MacroInstruction macroInstruction = instructionTranslator.translate(expressions.get(1));
        JumperInstruction jumperInstruction = new JumperInstruction();
        jumperInstruction.setArgumentsNumber(0);
        jumperInstruction.setInstructionCode(InstructionCode.JMPF);
        macroInstruction.add(jumperInstruction);
        return macroInstruction;
    }
}
