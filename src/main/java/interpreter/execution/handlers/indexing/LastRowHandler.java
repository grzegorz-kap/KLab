package interpreter.execution.handlers.indexing;

import interpreter.translate.model.InstructionCode;
import interpreter.types.Sizeable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LastRowHandler extends AbstractCellInstructionHandler {
    @Override
    protected long index(Sizeable sizeable) {
        return sizeable.getRows();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MLASTROW;
    }
}
