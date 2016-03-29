package com.klab.interpreter.core;

import com.klab.common.EventService;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class InterpreterImpl implements Interpreter {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterImpl.class);
    private InterpreterService interpreterService;
    private EventService eventService;

    @Override
    public void startSync(String input) {
        start(input, false);
    }

    @Override
    @Async
    public void startAsync(String input) {
        start(input, true);
    }

    public void start(String input, boolean events) {
        Interpreter.MAIN_LOCK.lock();
        LOGGER.info("\n{}", input);
        if (events) {
            eventService.publish(new ExecutionStartedEvent(this));
        }
        try {
            interpreterService.startExecution(input);
        } catch (RuntimeException ex) {
            LOGGER.error("Execution failed", ex);
        } finally {
            Interpreter.MAIN_LOCK.unlock();
            if (events) {
                eventService.publish(new ExecutionCompletedEvent(this));
            }
        }
    }

    @Autowired
    public void setInterpreterService(InterpreterService interpreterService) {
        this.interpreterService = interpreterService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
