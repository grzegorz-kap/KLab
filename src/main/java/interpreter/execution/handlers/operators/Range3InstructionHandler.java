package interpreter.execution.handlers.operators;

import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.matrix.MatrixFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Range3InstructionHandler extends AbstractInstructionHandler {
    private MatrixFactory<Double> matrixFactory;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        Number end = getNumber(executionContext.executionStackPop());
        Number step = getNumber(executionContext.executionStackPop());
        Number start = getNumber(executionContext.executionStackPop());
        executionContext.executionStackPush(matrixFactory.createRange(start, step, end));
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.RANGE3;
    }

    @Autowired
    public void setMatrixFactory(MatrixFactory<Double> matrixFactory) {
        this.matrixFactory = matrixFactory;
    }
}
