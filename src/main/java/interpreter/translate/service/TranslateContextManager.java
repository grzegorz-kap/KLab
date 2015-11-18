package interpreter.translate.service;

import interpreter.translate.model.Instruction;
import interpreter.translate.model.TranslateContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TranslateContextManager {

    private TranslateContext translateContext;

    public void setTranslateContext(TranslateContext translateContext) {
        this.translateContext = translateContext;
    }

    public void addInstruction(Instruction instruction) {
        translateContext.getMacroInstruction().add(instruction);
    }
}
