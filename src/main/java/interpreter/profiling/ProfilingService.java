package interpreter.profiling;

import interpreter.execution.InstructionAction;
import interpreter.execution.handlers.InstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProfilingService implements InstructionAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilingService.class);
    private HashMap<Instruction, ProfilingData<Instruction>> data = new HashMap<>();

    @Override
    public void handle(InstructionHandler handler, InstructionPointer pointer) {
        Instruction instruction = pointer.currentInstruction();
        ProfilingData<Instruction> pd = data.get(instruction);
        if (pd == null) {
            data.put(instruction, pd = new ProfilingData<>(instruction));
        }
        long start = System.nanoTime();
        handler.handle(pointer);
        long end = System.nanoTime();
        pd.addTime(end-start);
        pd.addCount(1L);
        LOGGER.info("{}, took: {}", instruction, pd);
    }
}
