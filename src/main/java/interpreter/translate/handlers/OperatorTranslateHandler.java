package interpreter.translate.handlers;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.operators.OperatorCode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import interpreter.translate.factory.OperatorInstructionCodesFactory;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OperatorTranslateHandler extends AbstractTranslateHandler {
    private OperatorInstructionCodesFactory operatorInstructionCodesFactory;

    @Override
    public void handle(Expression<ParseToken> expression) {
        OperatorToken operatorToken = (OperatorToken) expression.getValue();
        if(OperatorCode.PLUS.equals(operatorToken.getOperatorCode())) {
        	return;
        }
        if (OperatorCode.ASSIGN.equals(operatorToken.getOperatorCode())) {
            expression.setProperty(Expression.ANS_PROPERTY_KEY, false);
        }
        InstructionCode instructionCode = operatorInstructionCodesFactory.get(operatorToken.getOperatorCode());
        if (Objects.isNull(instructionCode)) {
            throw new RuntimeException();
        }
        Instruction instruction = new Instruction();
        instruction.setInstructionCode(instructionCode);
        tCM.addInstruction(instruction);
    }

    @Override
    public ParseClass getSupportedParseClass() {
        return ParseClass.OPERATOR;
    }

    @Autowired
    public void setOperatorInstructionCodesFactory(OperatorInstructionCodesFactory operatorInstructionCodesFactory) {
        this.operatorInstructionCodesFactory = operatorInstructionCodesFactory;
    }
}
