package com.klab.interpreter.profiling;

import com.google.common.collect.Sets;
import com.klab.interpreter.execution.InstructionAction;
import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.profiling.model.ProfilingData;
import com.klab.interpreter.translate.model.Instruction;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Component
public class ProfilingServiceImpl implements InstructionAction, ProfilingService {
    private Set<Code> measuredSet = Sets.newHashSet();

    @Override
    public void handle(InstructionHandler handler, InstructionPointer ip) {
        Instruction instruction = ip.currentInstruction();
        measuredSet.add(ip.getCode());
        ProfilingData<Instruction> pd = instruction.getProfilingData();
        if (pd == null) {
            pd = new ProfilingData<>(instruction);
            instruction.setProfilingData(pd);
        }
        long start = System.nanoTime();
        handler.handle(ip);
        long end = System.nanoTime();
        pd.addTime(end - start);
        pd.addCount(1L);
    }

    @Override
    public void clear() {
        measuredSet.clear();
    }

    @Override
    public Collection<Code> measured() {
        return Collections.unmodifiableCollection(measuredSet);
    }
}
