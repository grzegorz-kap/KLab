package com.klab.interpreter.core;

import com.klab.common.EventService;
import com.klab.interpreter.core.code.CodeGenerator;
import com.klab.interpreter.core.events.ErrorEvent;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.execution.service.ExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterpreterImpl implements Interpreter {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterImpl.class);
    private ExecutionService executionService;
    private CodeGenerator codeGenerator;
    private EventService eventService;

    @Override
    public List<Code> callStack() {
        return executionService.callStack();
    }

    @Override
    public void start(ExecutionCommand cmd, boolean isAsync) {
        Interpreter.MAIN_LOCK.lock();
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
            Interpreter.MAIN_LOCK.unlock();
            if (isAsync) {
                eventService.publish(new ExecutionCompletedEvent(cmd, this));
            }
        }
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
    private void setExecutionService(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Autowired
    private void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Autowired
    private void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
