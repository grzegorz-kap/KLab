package com.klab.interpreter.functions.external;

import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;

public class EndFunctionInstruction extends Instruction {
    private int outputStart;
    private int outputSize;
    private int expectedOutput;

    public EndFunctionInstruction(int outputStart, int outputSize) {
        setInstructionCode(InstructionCode.FUNCTION_END);
        setArgumentsNumber(0);
        this.outputStart = outputStart;
        this.outputSize = outputSize;
    }

    public int getOutputStart() {
        return outputStart;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public int getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(int expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
}
