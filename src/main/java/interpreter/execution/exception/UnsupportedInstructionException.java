package interpreter.execution.exception;

import interpreter.translate.model.instruction.Instruction;

public class UnsupportedInstructionException extends RuntimeException {
    private Instruction instruction;

    public UnsupportedInstructionException(String message, Instruction instruction) {
        super(message);
        this.instruction = instruction;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " ," + instruction.getInstructionCode();
    }
}
