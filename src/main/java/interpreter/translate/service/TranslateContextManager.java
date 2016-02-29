package interpreter.translate.service;

import interpreter.lexer.model.CodeAddress;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.model.TranslateContext;

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
