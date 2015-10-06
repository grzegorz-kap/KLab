package interpreter.parsing.handlers;

import interpreter.lexer.model.TokenClass;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InstructionEndHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        parseContextManager.setInstructionStop(true);
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return null;
    }
}
