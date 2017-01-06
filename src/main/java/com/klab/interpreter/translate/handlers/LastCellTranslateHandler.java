package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.CallToken;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.TokenIdentifierObject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LastCellTranslateHandler extends AbstractTranslateHandler {
    @Override
    public void handle(Expression<ParseToken> expression) {
        Expression<ParseToken> child = expression;
        Expression<ParseToken> parent = expression.getParent();
        while (parent != null && parent.getValue().getParseClass() != ParseClass.CALL) {
            child = child.getParent();
            parent = parent.getParent();
        }
        if (parent == null || parent.getChildren().size() < 1 || parent.getChildren().size() > 2) {
            throw new RuntimeException();
        }
        InstructionCode code;
        if (parent.getChildren().size() == 1) {
            code = InstructionCode.MLASTCELL;
        } else {
            code = parent.getChildren().get(0) == child ? InstructionCode.MLASTROW : InstructionCode.MLASTCOLUMN;
        }

        CallToken cl = (CallToken) parent.getValue();
        if (cl.getVariableAddress() == null) {
            throw new RuntimeException(); // TODO
        }
        TokenIdentifierObject idData = new TokenIdentifierObject(cl.getCallName(), cl.getVariableAddress());
        Instruction instruction = new Instruction(code, 0, idData);
        tCM.addInstruction(instruction, expression.getValue().getAddress());
    }

    @Override
    public ParseClass supportedParseClass() {
        return ParseClass.LAST_CELL;
    }
}
