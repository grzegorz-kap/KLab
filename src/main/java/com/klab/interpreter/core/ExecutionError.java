package com.klab.interpreter.core;

import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.translate.model.Instruction;
import org.apache.commons.lang3.StringUtils;

public class ExecutionError extends RuntimeException {
    private Instruction instruction;
    private String scriptId;

    public ExecutionError(String message, String scriptId, Throwable cause, Instruction instruction) {
        super(message, cause);
        this.instruction = instruction;
        this.scriptId = scriptId;
    }

    public String getAddress() {
        if (instruction != null) {
            StringBuilder builder = new StringBuilder();
            if (StringUtils.isNoneBlank(scriptId)) {
                builder.append(scriptId).append(", ");
            }
            CodeAddress a = instruction.getCodeAddress();
            if (a != null) {
                builder.append(String.format("Line: %d, Column: %d", a.getLine(), a.getColumn()));
            }
            return builder.toString();
        } else {
            return "";
        }
    }
}
