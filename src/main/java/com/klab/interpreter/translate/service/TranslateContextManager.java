package com.klab.interpreter.translate.service;

import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.model.TranslateContext;

import java.util.List;

public class TranslateContextManager {
    private TranslateContext translateContext;

    public CodeAddress previousCodeAddress() {
        List<Instruction> instructions = translateContext.getMacroInstruction().get();
        Instruction reduce = instructions.stream().filter(i -> i.getCodeAddress() != null).reduce((a, b) -> b).orElse(null);
        return reduce != null ? reduce.getCodeAddress() : null;
    }

    public void addInstruction(Instruction instruction, CodeAddress codeAddress) {
        translateContext.getMacroInstruction().add(instruction, codeAddress);
    }

    public void addInstruction(MacroInstruction macroInstruction) {
        translateContext.getMacroInstruction().add(macroInstruction);
    }

    void setTranslateContext(TranslateContext translateContext) {
        this.translateContext = translateContext;
    }
}
