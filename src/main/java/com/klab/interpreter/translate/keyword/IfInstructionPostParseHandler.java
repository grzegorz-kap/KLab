package com.klab.interpreter.translate.keyword;

import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.keyword.exception.KeywordParseException;
import com.klab.interpreter.translate.keyword.model.IfInstructionContext;
import com.klab.interpreter.translate.model.JumperInstruction;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.service.InstructionTranslator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.klab.interpreter.parsing.model.expression.Expression.PRINT_PROPERTY_KEY;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IfInstructionPostParseHandler extends AbstractPostParseHandler {

    private IfInstructionContext ifInstructionContext = new IfInstructionContext();

    @Override
    public boolean canBeHandled(List<Expression<ParseToken>> expressions) {
        return isIfStart(expressions) || isIfEnd(expressions) || isElse(expressions) || isElseIf(expressions);
    }

    @Override
    public boolean isInstructionCompletelyTranslated() {
        return ifInstructionContext.size() == 0;
    }

    @Override
    public MacroInstruction handle(List<Expression<ParseToken>> expressions,
                                   InstructionTranslator instructionTranslator) {
        if (isIfStart(expressions)) {
            return handleIFStart(expressions, instructionTranslator);
        }
        if (isIfEnd(expressions)) {
            return handleIfEnd(expressions, instructionTranslator);
        }
        if (isElse(expressions)) {
            return handleElse(expressions, instructionTranslator);
        }
        if (isElseIf(expressions)) {
            return handleElseIf(expressions, instructionTranslator);
        }
        return new MacroInstruction();
    }

    private MacroInstruction handleElseIf(List<Expression<ParseToken>> expressions, InstructionTranslator translator) {
        setupNoPrintNoAns(expressions, 1);
        JumperInstruction jumperInstruction = createJumpOnFalse();
        JumperInstruction jumpEndInstruction = createJmpInstruction();
        setupOnFalseOrThrow(1);
        ifInstructionContext.addEndIfJumper(jumpEndInstruction);
        ifInstructionContext.setJumpOnFalse(jumperInstruction);
        CodeAddress address = expressions.get(0).getValue().getAddress();
        return new MacroInstruction()
                .add(jumpEndInstruction, address)
                .add(translator.translate(expressions.get(1)))
                .add(jumperInstruction, address);
    }

    private MacroInstruction handleIFStart(List<Expression<ParseToken>> expressions, InstructionTranslator translator) {
        ifInstructionContext.addIf();
        setupNoPrintNoAns(expressions, 1);
        JumperInstruction jumperInstruction = createJumpOnFalse();
        ifInstructionContext.setJumpOnFalse(jumperInstruction);
        return translator.translate(expressions.get(1))
                .add(jumperInstruction, expressions.get(0).getValue().getAddress());
    }

    private MacroInstruction handleElse(List<Expression<ParseToken>> expressions, InstructionTranslator translator) {
        JumperInstruction jumperInstruction = createJmpInstruction();
        ifInstructionContext.addEndIfJumper(jumperInstruction);
        setupOnFalseOrThrow(1);
        expressions.get(0).setProperty(PRINT_PROPERTY_KEY, false);
        return new MacroInstruction()
                .add(jumperInstruction, expressions.get(0).getValue().getAddress());
    }

    private MacroInstruction handleIfEnd(List<Expression<ParseToken>> expressions, InstructionTranslator translator) {
        setupJumpOnFalse();
        int addressToJump = code.size();
        ifInstructionContext.forEachEndIfJumper(jumperInstruction -> jumperInstruction.setJumpIndex(addressToJump));
        expressions.get(0).setProperty(Expression.PRINT_PROPERTY_KEY, false);
        ifInstructionContext.removeLastIf();
        return new MacroInstruction();
    }

    private boolean isIfEnd(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 1 && isParseClass(expressions, ParseClass.END_IF, 0);
    }

    private boolean isIfStart(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 2 && isParseClass(expressions, ParseClass.IF, 0);
    }

    private boolean isElse(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 1 && isParseClass(expressions, ParseClass.ELSE_KEYWORD, 0);
    }

    private boolean isElseIf(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 2 && isParseClass(expressions, ParseClass.ELSEIF_KEYWORD, 0);
    }

    private void setupJumpOnFalse() {
        int addressToJump = code.size();
        JumperInstruction jumpOnFalse = ifInstructionContext.getJumpOnFalse();
        if (Objects.nonNull(jumpOnFalse)) {
            jumpOnFalse.setJumpIndex(addressToJump);
            ifInstructionContext.setJumpOnFalse(null);
        }
    }

    private void setupOnFalseOrThrow(int offset) {
        JumperInstruction jumperInstruction = ifInstructionContext.getJumpOnFalse();
        if (Objects.isNull(jumperInstruction)) {
            throw new KeywordParseException(KeywordParseException.UNEXPECTED_ELSE_OR_ELSEIF);
        }
        jumperInstruction.setJumpIndex(code.size() + offset);
        ifInstructionContext.setJumpOnFalse(null);
    }

}
