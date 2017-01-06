package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.CallToken;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.CallInstruction;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallTranslateHandler extends AbstractTranslateHandler {
    @Override
    public void handle(Expression<ParseToken> expression) {
        CallToken callToken = (CallToken) expression.getValue();
        CallInstruction callInstruction = new CallInstruction(callToken);
        callInstruction.setArgumentsNumber(expression.getChildrenCount());
        resolveOutputSize(callInstruction, expression);
        tCM.addInstruction(callInstruction, address(expression));
    }

    private void resolveOutputSize(CallInstruction instruction, Expression<ParseToken> expression) {
        instruction.setExpectedOutputSize(-1);
        Expression<ParseToken> parent = expression.getParent();
        if (parent != null && parent.getValue() != null) {
            instruction.setExpectedOutputSize(1);
            if (parent.getValue().getToken().getLexeme().equals("=")) {
                Expression<ParseToken> left = parent.getChildren().get(0); // TODO check arguments number
                if (ParseClass.MATRIX.equals(left.getValue().getParseClass())) {
                    instruction.setExpectedOutputSize(left.getChildren().get(0).getChildren().size());
                }
            }
        }
    }

    @Override
    public ParseClass supportedParseClass() {
        return ParseClass.CALL;
    }
}
