package interpreter.execution.handlers;

import interpreter.commons.ObjectData;
import interpreter.execution.model.InstructionPointer;
import interpreter.math.matrix.MatrixBuilder;
import interpreter.math.matrix.MatrixFactory;
import interpreter.math.scalar.DoubleScalar;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixVerseInstructionHandler extends AbstractInstructionHandler {

    private MatrixFactory matrixFactory;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        Instruction instruction = instructionPointer.current();
        MatrixBuilder<Double> matrixBuilder = matrixFactory.createDoubleBuilder();
        int columnsNumber = instruction.getArgumentsNumber();
        while (columnsNumber-- > 0) {
            process(matrixBuilder, executionContext.executionStackPop());
        }
        instructionPointer.increment();
        executionContext.pushToExecutionStack(matrixBuilder.build());
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MATRIX_VERSE;
    }

    @Autowired
    public void setMatrixFactory(MatrixFactory matrixFactory) {
        this.matrixFactory = matrixFactory;
    }

    private void process(MatrixBuilder<Double> builder, ObjectData objectData) {
        if (objectData instanceof DoubleScalar) {
            builder.appendRight(((DoubleScalar) objectData).getValue());
        } else {
            throw new RuntimeException();
        }
    }
}
