package interpreter.core;

import interpreter.execution.model.Code;
import interpreter.lexer.model.TokenList;

public interface CodeGenerator {
    Code translate(String input);

    Code translate(TokenList input);
}
