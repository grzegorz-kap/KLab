package interpreter.execution;

import interpreter.execution.handlers.InstructionHandler;
import interpreter.execution.model.InstructionPointer;

public interface InstructionAction {
    void handle(InstructionHandler handler, InstructionPointer pointer);
}
