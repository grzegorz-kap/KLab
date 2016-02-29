package interpreter.core.code;

import interpreter.execution.model.Code;
import interpreter.lexer.model.TokenList;

import java.util.function.Supplier;

public interface CodeGenerator {
    Code translate(String input);

    Code translate(String input, Supplier<Code> codeSupplier, MacroInstructionTranslatedCallback macroInstructionTranslatedCallback);

    Code translate(TokenList input);

    boolean executionCanStart();
}
