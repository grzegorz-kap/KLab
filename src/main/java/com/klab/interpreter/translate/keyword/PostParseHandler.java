package com.klab.interpreter.translate.keyword;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.service.InstructionTranslator;

import java.util.List;

public interface PostParseHandler {

    boolean canBeHandled(List<Expression<ParseToken>> expressions);

    boolean executionCanStart();

    MacroInstruction handle(List<Expression<ParseToken>> expressions, InstructionTranslator instructionTranslator);

    void setCode(Code code);
}
