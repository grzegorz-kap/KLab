package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.service.functions.model.CallToken;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.TokenIdentifierObject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixAllTranslateHandler extends AbstractTranslateHandler {
    @Override
    public void handle(Expression<ParseToken> expression) {
        Expression<ParseToken> parent = expression.getParent();
        if (parent == null || !parent.getValue().getParseClass().equals(ParseClass.CALL)) {
            throw new RuntimeException(); // TODO;
        }
        if (parent.getChildren().size() < 1 || parent.getChildren().size() > 2) {
            throw new RuntimeException(); // TODO;
        }

        InstructionCode code;
        if (parent.getChildren().size() == 1) {
            code = InstructionCode.MALLCELL;
        } else {
            code = parent.getChildren().get(0) == expression ? InstructionCode.MALLROWS : InstructionCode.MALLCOLS;
        }

        CallToken address = ((CallToken) parent.getValue());
        if (address.getVariableAddress() == null) {
            throw new RuntimeException(); // TODO
        }
        TokenIdentifierObject idObject = new TokenIdentifierObject(address.getCallName(), address.getVariableAddress());
        tCM.addInstruction(new Instruction(code, 0, idObject), address(expression));
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.MATRIX_ALL;
    }
}
