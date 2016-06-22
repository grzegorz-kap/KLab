package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.matrix.MatrixNewColumnParseHandler;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.service.ParseContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommaParseHandler extends AbstractParseHandler {
    private MatrixNewColumnParseHandler matrixNewColumnParseHandler;
    private InstructionEndHandler instructionEndHandler;
    private FunctionArgumentDelimiterParseHandler functionArgumentDelimiterParseHandler;

    @Override
    public void handle() {
        if (parseContextManager.getBalanceContext().isBalanceType(BalanceType.INSIDE_MATRIX)) {
            matrixNewColumnParseHandler.handle();
        } else if (parseContextManager.getBalanceContext().isBalanceType(BalanceType.FUNCTION_ARGUMENTS)) {
            functionArgumentDelimiterParseHandler.handle();
        } else {
            instructionEndHandler.handle();
        }
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.COMMA;
    }

    @Override
    public void setParseContextManager(ParseContextManager parseContextManager) {
        super.setParseContextManager(parseContextManager);
        matrixNewColumnParseHandler.setParseContextManager(parseContextManager);
        instructionEndHandler.setParseContextManager(parseContextManager);
        functionArgumentDelimiterParseHandler.setParseContextManager(parseContextManager);
    }

    @Autowired
    public void setMatrixNewColumnParseHandler(MatrixNewColumnParseHandler matrixNewColumnParseHandler) {
        this.matrixNewColumnParseHandler = matrixNewColumnParseHandler;
    }

    @Autowired
    private void setInstructionEndHandler(InstructionEndHandler instructionEndHandler) {
        this.instructionEndHandler = instructionEndHandler;
    }

    @Autowired
    public void setFunctionArgumentDelimiterParseHandler(FunctionArgumentDelimiterParseHandler functionArgumentDelimiterParseHandler) {
        this.functionArgumentDelimiterParseHandler = functionArgumentDelimiterParseHandler;
    }
}
