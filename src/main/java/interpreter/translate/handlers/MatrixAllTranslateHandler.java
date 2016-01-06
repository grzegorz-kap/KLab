package interpreter.translate.handlers;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.service.functions.model.CallToken;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.TokenIdentifierObject;
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
        translateContextManager.addInstruction(new Instruction(code, 0, new TokenIdentifierObject(address.getCallName(), address.getVariableAddress())));
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.MATRIX_ALL;
    }
}
