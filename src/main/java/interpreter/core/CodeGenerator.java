package interpreter.core;

import interpreter.execution.model.Code;

public interface CodeGenerator {
    Code translate(String input);
}
