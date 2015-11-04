package interpreter.parsing.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.handlers.matrix.MatrixNewColumnParseHandler;
import interpreter.parsing.model.BalanceType;
import interpreter.parsing.service.ParseContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommaParseHandler extends AbstractParseHandler {

    private MatrixNewColumnParseHandler matrixNewColumnParseHandler;
    private InstructionEndHandler instructionEndHandler;
    private FunctionArgumentDelimiterParseHandler argumentDilimiterHandler;

    @Override
    public void handle() {
        if (isThisCommaInsideMatrix()) {
            matrixNewColumnParseHandler.handle();
        } else if (isThisCommaInsideFunctionCall()) {
            argumentDilimiterHandler.handle();
        } else {
            instructionEndHandler.handle();
        }
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.COMMA;
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        super.setContextManager(parseContextManager);
        matrixNewColumnParseHandler.setContextManager(parseContextManager);
        instructionEndHandler.setContextManager(parseContextManager);
        argumentDilimiterHandler.setContextManager(parseContextManager);
    }

    private boolean isThisCommaInsideMatrix() {
        return parseContextManager.getBalanceContext().isBalanceType(BalanceType.INSIDE_MATRIX);
    }

    private boolean isThisCommaInsideFunctionCall() {
        return parseContextManager.getBalanceContext().isBalanceType(BalanceType.FUNCTION_ARGUMENTS);
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
    public void setArgumentDilimiterHandler(FunctionArgumentDelimiterParseHandler argumentDilimiterHandler) {
        this.argumentDilimiterHandler = argumentDilimiterHandler;
    }
}
