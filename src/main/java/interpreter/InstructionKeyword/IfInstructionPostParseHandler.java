package interpreter.InstructionKeyword;

import static interpreter.InstructionKeyword.exception.KeywordParseException.UNEXPECTED_ELSE_OR_ELSEIF;
import static interpreter.parsing.model.expression.Expression.PRINT_PROPERTY_KEY;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.InstructionKeyword.exception.KeywordParseException;
import interpreter.InstructionKeyword.model.IfInstructionContext;
import interpreter.execution.model.Code;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.model.InstructionCode;
import interpreter.translate.model.JumperInstruction;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.service.InstructionTranslator;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IfInstructionPostParseHandler implements PostParseHandler {

	private IfInstructionContext ifInstructionContext = new IfInstructionContext();
	private Code code;

	@Override
	public boolean canBeHandled(List<Expression<ParseToken>> expressions) {
		return isIfStart(expressions) || isIfEnd(expressions) || isElse(expressions) || isElseIf(expressions);
	}

	@Override
	public boolean executionCanStart() {
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

	@Override
	public void setCode(Code code) {
		this.code = code;
	}

	private MacroInstruction handleElseIf(List<Expression<ParseToken>> expressions,
			InstructionTranslator instructionTranslator) {
		setupProperties(expressions);
		JumperInstruction jumperInstruction = createJumpOnFalse();
		JumperInstruction jumpEndInstruction = createJmpInstruction();
		setupOnFalseOrThrow(1);
		ifInstructionContext.addEndIfJumper(jumpEndInstruction);
		ifInstructionContext.setJumpOnFalse(jumperInstruction);
		return new MacroInstruction().add(jumpEndInstruction).add(instructionTranslator.translate(expressions.get(1)))
				.add(jumperInstruction);
	}

	private MacroInstruction handleIFStart(List<Expression<ParseToken>> expressions,
			InstructionTranslator instructionTranslator) {
		ifInstructionContext.addIf();
		setupProperties(expressions);
		JumperInstruction jumperInstruction = createJumpOnFalse();
		ifInstructionContext.setJumpOnFalse(jumperInstruction);
		return instructionTranslator.translate(expressions.get(1)).add(jumperInstruction);
	}

	private MacroInstruction handleElse(List<Expression<ParseToken>> expressions,
			InstructionTranslator instructionTranslator) {
		JumperInstruction jumperInstruction = createJmpInstruction();
		ifInstructionContext.addEndIfJumper(jumperInstruction);
		setupOnFalseOrThrow(1);
		expressions.get(0).setProperty(PRINT_PROPERTY_KEY, false);
		return new MacroInstruction().add(jumperInstruction);
	}

	private MacroInstruction handleIfEnd(List<Expression<ParseToken>> expressions,
			InstructionTranslator instructionTranslator) {
		setupJumpOnFalse();
		int addressToJump = code.size();
		ifInstructionContext.forEachEndIfJumper(jumperInstruction -> jumperInstruction.setJumpIndex(addressToJump));
		expressions.get(0).setProperty(Expression.PRINT_PROPERTY_KEY, false);
		ifInstructionContext.removeLastIf();
		return new MacroInstruction();
	}

	private JumperInstruction createJmpInstruction() {
		JumperInstruction jumperInstruction = new JumperInstruction();
		jumperInstruction.setArgumentsNumber(0);
		jumperInstruction.setInstructionCode(InstructionCode.JMP);
		return jumperInstruction;
	}

	private JumperInstruction createJumpOnFalse() {
		JumperInstruction jumperInstruction = new JumperInstruction();
		jumperInstruction.setArgumentsNumber(0);
		jumperInstruction.setInstructionCode(InstructionCode.JMPF);
		return jumperInstruction;
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

	private boolean isElse(List<Expression<ParseToken>> expressions) {
		return expressions.size() == 1 && isParseClass(expressions, ParseClass.ELSE_KEYWORD);
	}

	private boolean isElseIf(List<Expression<ParseToken>> expressions) {
		return expressions.size() == 2 && isParseClass(expressions, ParseClass.ELSEIF_KEYWORD);
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
			throw new KeywordParseException(UNEXPECTED_ELSE_OR_ELSEIF);
		}
		jumperInstruction.setJumpIndex(code.size() + offset);
		ifInstructionContext.setJumpOnFalse(null);
	}

	private void setupProperties(List<Expression<ParseToken>> expressions) {
		expressions.get(1).setProperty(Expression.PRINT_PROPERTY_KEY, false);
		expressions.get(1).setProperty(Expression.ANS_PROPERTY_KEY, false);
	}
}