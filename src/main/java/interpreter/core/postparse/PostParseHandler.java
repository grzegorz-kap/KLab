package interpreter.core.postparse;

import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.service.InstructionTranslator;

import java.util.List;

public interface PostParseHandler {

    boolean canBeHandled(List<Expression<ParseToken>> expressions);

    MacroInstruction handle(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator);
}
