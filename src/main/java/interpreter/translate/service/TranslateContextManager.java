package interpreter.translate.service;

import interpreter.translate.model.Instruction;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.model.TranslateContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

public class TranslateContextManager {
    private TranslateContext translateContext;

    public void setTranslateContext(TranslateContext translateContext) {
        this.translateContext = translateContext;
    }

    public void addInstruction(Instruction instruction) {
        translateContext.getMacroInstruction().add(instruction);
    }

    public void addInstruction(MacroInstruction macroInstruction) {
        translateContext.getMacroInstruction().add(macroInstruction);
    }
}
