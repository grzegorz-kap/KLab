package interpreter.service.functions;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.service.functions.model.CallInstruction;
import interpreter.service.functions.model.CallToken;
import interpreter.translate.handlers.AbstractTranslateHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallTranslateHandler extends AbstractTranslateHandler {

    // TODO check if assignment target and check how many output arguments
    @Override
    public void handle(Expression<ParseToken> expression) {
        CallToken callToken = (CallToken) expression.getValue();
        CallInstruction callInstruction = new CallInstruction(callToken);
        callInstruction.setArgumentsNumber(expression.getChildrenCount());
        resolveOutputSize(callInstruction, expression);
        translateContextManager.addInstruction(callInstruction);
    }

    private void resolveOutputSize(CallInstruction instruction, Expression<ParseToken> expression) {
        instruction.setExpectedOutputSize(1); // TODO support more than one argument;
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.CALL;
    }

}
