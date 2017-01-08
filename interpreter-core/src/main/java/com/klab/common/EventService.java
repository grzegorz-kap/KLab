package com.klab.common;

import com.klab.interpreter.core.events.InterpreterEvent;

public interface EventService {
    void publish(InterpreterEvent<?> event);

    void publishAsync(InterpreterEvent<?> event);
}
