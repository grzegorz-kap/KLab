package com.klab.interpreter.core.code;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.lexer.model.TokenList;

import java.util.function.Supplier;

public interface CodeGenerator {
    Code translate(String input);

    Code translate(String input, Supplier<Code> codeSupplier, MacroInstructionTranslatedCallback macroInstructionTranslatedCallback);

    Code translate(TokenList input);

    boolean executionCanStart();
}
