package com.klab.interpreter.core.code;

import com.klab.interpreter.execution.model.Code;

public interface ScriptService {
    Code getCode(String scriptName);
}
