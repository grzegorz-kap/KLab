package interpreter.service.functions.external;

import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;

public class EndFunctionInstruction extends Instruction {
    private int outputStart;
    private int outputSize;

    public EndFunctionInstruction(int outputStart, int outputSize) {
        setInstructionCode(InstructionCode.FUNCTION_END);
        setArgumentsNumber(0);
        this.outputStart = outputStart;
        this.outputSize = outputSize;
    }

    public int getOutputStart() {
        return outputStart;
    }

    public void setOutputStart(int outputStart) {
        this.outputStart = outputStart;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = outputSize;
    }
}
