package interpreter.core;

import interpreter.execution.model.Code;

public interface ScriptService {
    Code getCode(String scriptName);
}
