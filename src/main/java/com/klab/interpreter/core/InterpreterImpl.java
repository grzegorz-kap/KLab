package com.klab.interpreter.core;

import com.klab.common.EventService;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InterpreterImpl implements Interpreter {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterImpl.class);
    private InterpreterService interpreterService;
    private EventService eventService;

    @Override
    public void startSync(ExecutionCommand cmd) {
        start(cmd, false);
    }

    @Override
    @Async
    public void startAsync(ExecutionCommand cmd) {
        start(cmd, true);
    }

    public void start(ExecutionCommand cmd, boolean events) {
        Interpreter.MAIN_LOCK.lock();
        LOGGER.info("\n{}", cmd.getBody());
        if (events) {
            eventService.publish(new ExecutionStartedEvent(cmd, this));
        }
        try {
            interpreterService.startExecution(cmd);
        } finally {
            Interpreter.MAIN_LOCK.unlock();
            if (events) {
                eventService.publish(new ExecutionCompletedEvent(cmd, this));
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
