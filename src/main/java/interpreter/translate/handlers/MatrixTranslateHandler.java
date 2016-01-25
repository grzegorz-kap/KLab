package interpreter.translate.handlers;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixTranslateHandler extends AbstractTranslateHandler {

    @Override
    public void handle(Expression<ParseToken> expression) {
        Instruction instruction = new Instruction();
        instruction.setArgumentsNumber(expression.getChildrenCount());
        instruction.setInstructionCode(InstructionCode.MATRIX);
        tCM.addInstruction(instruction);
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.MATRIX;
    }
}
