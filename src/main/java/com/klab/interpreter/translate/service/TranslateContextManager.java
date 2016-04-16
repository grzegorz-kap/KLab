package com.klab.interpreter.translate.service;

import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.model.TranslateContext;

public class TranslateContextManager {
    private TranslateContext translateContext;

    public void addInstruction(Instruction instruction, CodeAddress codeAddress) {
        translateContext.getMacroInstruction().add(instruction, codeAddress);
    }

    public void addInstruction(MacroInstruction macroInstruction) {
        translateContext.getMacroInstruction().add(macroInstruction);
    }

    public void setTranslateContext(TranslateContext translateContext) {
        this.translateContext = translateContext;
    }
}
