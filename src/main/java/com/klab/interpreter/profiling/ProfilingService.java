package com.klab.interpreter.profiling;

import com.klab.interpreter.execution.InstructionAction;
import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.Instruction;
import org.springframework.stereotype.Component;

@Component
public class ProfilingService implements InstructionAction {
    @Override
    public void handle(InstructionHandler handler, InstructionPointer pointer) {
        Instruction instruction = pointer.currentInstruction();
        ProfilingData<Instruction> pd = instruction.getProfilingData();
        if (pd == null) {
            instruction.setProfilingData(pd = new ProfilingData<>(instruction));
        }
        long start = System.nanoTime();
        handler.handle(pointer);
        long end = System.nanoTime();
        pd.addTime(end - start);
        pd.addCount(1L);
    }
}
