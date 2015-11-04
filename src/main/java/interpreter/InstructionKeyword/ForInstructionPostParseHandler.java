package interpreter.InstructionKeyword;

import interpreter.InstructionKeyword.exception.KeywordParseException;
import interpreter.InstructionKeyword.model.ForInstructionContext;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
import interpreter.translate.model.JumperInstruction;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.service.InstructionTranslator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static interpreter.InstructionKeyword.exception.KeywordParseException.FOR_KEYWORD_ASSIGNMENT_EXPECTED;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ForInstructionPostParseHandler extends AbstractPostParseHandler {
    private ForInstructionContext forInstructionContext = new ForInstructionContext();

    @Override
    public boolean canBeHandled(List<Expression<ParseToken>> expressions) {
        return isForStart(expressions) || isForEnd(expressions);
    }

    @Override
    public boolean executionCanStart() {
        return forInstructionContext.size() == 0;
    }

    @Override
    public MacroInstruction handle(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator) {
        if (isForStart(expressions)) {
            return handleForStart(expressions, instructionTranslator);
        }
        if (isForEnd(expressions)) {
            return handleForEnd(expressions, instructionTranslator);
        }
        return new MacroInstruction();
    }

    private MacroInstruction handleForStart(List<Expression<ParseToken>> expressions, InstructionTranslator translator) {
        setupNoPrintNoAns(expressions, 1);
        checkIfAssignOperator(expressions);
        JumperInstruction jumpOnFalse = createJumpOnFalse();
        MacroInstruction macroInstruction = translator.translate(expressions.get(1));
        int flhnextAddress = code.size() + macroInstruction.size() + 1; //points to FLHNEXT
        forInstructionContext.push(flhnextAddress, jumpOnFalse);
        return macroInstruction
                .add(new Instruction(InstructionCode.FLSTART, 0))   // for initialize
                .add(new Instruction(InstructionCode.FLHNEXT, 0))   // check iteration condition
                .add(jumpOnFalse)                                   // jump to for end
                .add(new Instruction(InstructionCode.FLGNEXT, 0));  // load next value to iterator
    }

    private void checkIfAssignOperator(List<Expression<ParseToken>> expressions) {
        Expression<ParseToken> root = expressions.get(1);
        if (!ParseClass.OPERATOR.equals(root.getValue().getParseClass())) {
            throw new KeywordParseException(FOR_KEYWORD_ASSIGNMENT_EXPECTED);
        }
        if (!((OperatorToken) root.getValue()).getOperatorCode().equals(OperatorCode.ASSIGN)) {
            throw new KeywordParseException(FOR_KEYWORD_ASSIGNMENT_EXPECTED);
        }
    }

    private MacroInstruction handleForEnd(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator) {
        JumperInstruction flhnextJump = createJmpInstruction();
        flhnextJump.setJumpIndex(forInstructionContext.getflhNextAddress());
        forInstructionContext.getJumpOnFalse().setJumpIndex(code.size() + 1); // jump to FLEND
        forInstructionContext.pop();
        return new MacroInstruction()
                .add(flhnextJump)
                .add(new Instruction(InstructionCode.FLEND, 0));
    }

    private boolean isForEnd(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 1 && isParseClass(expressions, ParseClass.ENDFOR_KEYWORD, 0);
    }

    private boolean isForStart(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 2 && isParseClass(expressions, ParseClass.FOR_KEYWORD, 0);
    }
}
