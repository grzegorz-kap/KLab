package interpreter.InstructionKeyword;

import interpreter.InstructionKeyword.exception.KeywordParseException;
import interpreter.InstructionKeyword.model.ForInstructionContext;
import interpreter.commons.IdentifierMapper;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import interpreter.translate.model.FLNextInstruction;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.JumperInstruction;
import interpreter.translate.model.MacroInstruction;
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
            return handleForEnd(expressions, instructionTranslator);
        }
        return new MacroInstruction();
    }

    private MacroInstruction handleForStart(List<Expression<ParseToken>> expressions, InstructionTranslator translator) {
        setupNoPrintNoAns(expressions, 1);
        checkIfAssignOperator(expressions);
        FLNextInstruction flnextInstruction = new FLNextInstruction();
        MacroInstruction macroInstruction = translator.translate(expressions.get(1));
        modifyAssignmentTarget(macroInstruction, flnextInstruction);
        int flhnextAddress = code.size() + macroInstruction.size(); //points to FLHNEXT
        forInstructionContext.push(flhnextAddress, flnextInstruction);
        return macroInstruction.add(flnextInstruction);
    }

    private void modifyAssignmentTarget(MacroInstruction macroInstruction, FLNextInstruction flnextInstruction) {
        Instruction instruction = macroInstruction.get(0);
        IdentifierObject id = (IdentifierObject) instruction.getObjectDate(0);
        String dataName = new StringBuilder("$").append(id.getId()).append("_data").toString();
        Integer dataAddress = identifierMapper.registerMainIdentifier(dataName);
        flnextInstruction.setDataId(new IdentifierObject(dataName, dataAddress));
        flnextInstruction.setIteratorId(new IdentifierObject(id.getId(), id.getAddress()));
        id.getIdentifierToken().setAddress(dataAddress);
        id.getIdentifierToken().setId(dataName);
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
        return new MacroInstruction().add(flhnextJump);

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
