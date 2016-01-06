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
public class LastCellTranslateHandler extends AbstractTranslateHandler {
    @Override
    public void handle(Expression<ParseToken> expression) {
        Expression<ParseToken> child = expression;
        Expression<ParseToken> parent = expression.getParent();
        while (parent != null && !parent.getValue().getParseClass().equals(ParseClass.CALL)) {
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
        tCM.addInstruction(new Instruction(code, 0, new TokenIdentifierObject(cl.getCallName(), cl.getVariableAddress())));
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.LAST_CELL;
    }
}
