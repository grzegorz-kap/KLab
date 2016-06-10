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
        if (isSemicolonInsideMatrix()) {
            matrixNewRowHandler.handle();
        } else {
            instructionEndHandler.handle();
        }
    }

    private boolean isSemicolonInsideMatrix() {
        return parseContextManager.getBalanceContext().isBalanceType(BalanceType.INSIDE_MATRIX);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.SEMICOLON;
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        super.setContextManager(parseContextManager);
        matrixNewRowHandler.setContextManager(parseContextManager);
        instructionEndHandler.setContextManager(parseContextManager);
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
