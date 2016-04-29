package com.klab.interpreter.execution.handlers.indexing;

import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.Sizeable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LastColumnHandler extends AbstractCellInstructionHandler {
    @Override
    protected long index(Sizeable sizeable) {
        return sizeable.getColumns();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MLASTCOLUMN;
    }
}
