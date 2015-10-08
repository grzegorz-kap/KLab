package interpreter.parsing.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.handlers.matrix.MatrixNewRowHandler;
import interpreter.parsing.model.BalanceType;
import interpreter.parsing.service.ParseContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NewLineParseHandler extends AbstractParseHandler {

    private InstructionEndHandler instructionEndHandler;
    private MatrixNewRowHandler matrixNewRowHandler;

    @Override
    public void handle() {
        if (isNewLineInsideMatrix()) {
            matrixNewRowHandler.handle();
        } else {
            instructionEndHandler.handle();
        }
    }

    private boolean isNewLineInsideMatrix() {
        return parseContextManager.getBalanceContext().isBalanceType(BalanceType.INSIDE_MATRIX);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.NEW_LINE;
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        super.setContextManager(parseContextManager);
        instructionEndHandler.setContextManager(parseContextManager);
        matrixNewRowHandler.setContextManager(parseContextManager);
    }

    @Autowired
    private void setInstructionEndHandler(InstructionEndHandler instructionEndHandler) {
        this.instructionEndHandler = instructionEndHandler;
    }

    @Autowired
    private void setMatrixNewRowHandler(MatrixNewRowHandler matrixNewRowHandler) {
        this.matrixNewRowHandler = matrixNewRowHandler;
    }
}
