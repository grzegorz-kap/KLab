package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.matrix.MatrixNewRowHandler;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.service.ParseContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SemicolonParseHandler extends AbstractParseHandler {
    private MatrixNewRowHandler matrixNewRowHandler;
    private InstructionEndHandler instructionEndHandler;

    @Override
    public void handle() {
        if (parseContextManager.getBalanceContext().isBalanceType(BalanceType.INSIDE_MATRIX)) {
            matrixNewRowHandler.handle();
        } else {
            instructionEndHandler.handle();
        }
    }

    @Override
    public TokenClass supportedTokenClass() {
        return TokenClass.SEMICOLON;
    }

    @Override
    public void setParseContextManager(ParseContextManager parseContextManager) {
        super.setParseContextManager(parseContextManager);
        matrixNewRowHandler.setParseContextManager(parseContextManager);
        instructionEndHandler.setParseContextManager(parseContextManager);
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
