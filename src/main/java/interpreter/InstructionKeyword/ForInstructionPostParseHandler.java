package interpreter.InstructionKeyword;

import interpreter.InstructionKeyword.exception.KeywordParseException;
import interpreter.InstructionKeyword.model.ForInstructionContext;
import interpreter.commons.IdentifierMapper;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import interpreter.translate.model.*;
import interpreter.translate.service.InstructionTranslator;
import interpreter.types.IdentifierObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static interpreter.InstructionKeyword.exception.KeywordParseException.FOR_KEYWORD_ASSIGNMENT_EXPECTED;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ForInstructionPostParseHandler extends AbstractPostParseHandler {
    private ForInstructionContext forInstructionContext = new ForInstructionContext();
    private IdentifierMapper identifierMapper;

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
            return handleForEnd();
        }
        return new MacroInstruction();
    }

    private MacroInstruction handleForStart(List<Expression<ParseToken>> expressions, InstructionTranslator translator) {
        setupNoPrintNoAns(expressions, 1);
        checkIfAssignOperator(expressions);
        FLNextInstruction flnextInstruction = new FLNextInstruction();
        MacroInstruction macroInstruction = translator.translate(expressions.get(1));
        forInstructionContext.push(code.size() + macroInstruction.size() + 1, flnextInstruction);
        findIteratorTarget(macroInstruction, flnextInstruction);
        return macroInstruction
                .add(createFlInit())
                .add(flnextInstruction);
    }

    private Instruction createFlInit() {
        Instruction instruction = new Instruction(InstructionCode.FLINIT, 0);
        String name = forInstructionContext.getName();
        instruction.add(new IdentifierObject(name, identifierMapper.getMainAddress(name)));
        return instruction;
    }

    private MacroInstruction handleForEnd() {
        JumperInstruction flhnextJump = createJmpInstruction();
        flhnextJump.setJumpIndex(forInstructionContext.getFlhNextAddress());
        forInstructionContext.getJumpOnFalse().setJumpIndex(code.size() + 1); // jump to FLEND
        forInstructionContext.pop();
        return new MacroInstruction().add(flhnextJump);
    }

    private void findIteratorTarget(MacroInstruction macroInstruction, FLNextInstruction flnextInstruction) {
        Instruction instruction = macroInstruction.get(0);
        IdentifierObject id = (IdentifierObject) instruction.getObjectData(0);
        forInstructionContext.setName(id.getId());
        flnextInstruction.setIteratorId(new IdentifierObject(id.getId(), id.getAddress()));
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

    private boolean isForEnd(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 1 && isParseClass(expressions, ParseClass.ENDFOR_KEYWORD, 0);
    }

    private boolean isForStart(List<Expression<ParseToken>> expressions) {
        return expressions.size() == 2 && isParseClass(expressions, ParseClass.FOR_KEYWORD, 0);
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }
}
