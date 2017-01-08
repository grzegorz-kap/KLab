package com.klab.interpreter.core.code;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.lexer.model.TokenList;

import java.util.function.Supplier;

public interface CodeGenerator {
    void reset();

    Code translate(String input);

    Code translate(String input, Supplier<Code> codeSupplier);

    Code translate(String input, Supplier<Code> codeSupplier, InstructionTranslatedCallback macroInstructionTranslatedCallback);

    Code translate(TokenList input);

    Code translate(TokenList input, Supplier<Code> codeSupplier);

    boolean isInstructionCompletelyTranslated();
}
