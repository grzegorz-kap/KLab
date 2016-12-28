package com.klab.interpreter.core;

import com.klab.common.EventService;
import com.klab.interpreter.core.code.CodeGenerator;
import com.klab.interpreter.core.events.ErrorEvent;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.execution.service.ExecutionService;
import com.klab.interpreter.profiling.ProfilingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class Interpreter {

    public static final Lock MAIN_LOCK = new ReentrantLock();

    private static final Logger LOGGER = LoggerFactory.getLogger(Interpreter.class);

    private ExecutionService executionService;
    private CodeGenerator codeGenerator;
    private EventService eventService;
    private Collection<ExecutionStartInitialization> executionStartInitializations;

    public List<Code> callStack() {
        return executionService.callStack();
    }

    @Async
    public void startAsync(ExecutionCommand cmd, boolean isAsync) {
        MAIN_LOCK.lock();
        executionStartInitializations.forEach(ExecutionStartInitialization::initialize);
        if (isAsync) {
            eventService.publish(new ExecutionStartedEvent(cmd, this));
        }
        try {
            startExecution(cmd);
        } catch (ExecutionError ex) {
            eventService.publish(new ErrorEvent(ex, this));
            LOGGER.error("", ex);
        } catch (Exception ex) {
            LOGGER.error("", ex);
        } finally {
            MAIN_LOCK.unlock();
            if (isAsync) {
                eventService.publish(new ExecutionCompletedEvent(cmd, this));
            }
        }
    }

    public void startSync(ExecutionCommand command) {
        startAsync(command, false);
    }

    private void startExecution(ExecutionCommand input) {
        executionService.resetCodeAndStack();
        codeGenerator.reset();
        if (input.isProfiling()) {
            executionService.enableProfiling();
        } else {
            executionService.disableProfiling();
        }
        codeGenerator.translate(input.getBody(), this::getCode, this::execute);
        if (!codeGenerator.isInstructionCompletelyTranslated()) {
            throw new RuntimeException("Not completed statement");
        }
    }

    private Code getCode() {
        return executionService.getExecutionContext().getCode();
    }

    private void execute() throws ExecutionError {
        if (codeGenerator.isInstructionCompletelyTranslated()) {
            executionService.start();
        }
    }

    @Autowired
    void setExecutionService(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Autowired
    void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Autowired
    void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    void setExecutionStartInitializations(Collection<ExecutionStartInitialization> executionStartInitializations) {
        this.executionStartInitializations = executionStartInitializations;
    }
}
