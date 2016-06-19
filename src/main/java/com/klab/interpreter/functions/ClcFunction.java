package com.klab.interpreter.functions;

import com.klab.common.EventService;
import com.klab.interpreter.core.events.ClearConsoleEvent;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClcFunction extends AbstractInternalFunction {
    private EventService eventService;

    public ClcFunction() {
        super(0, 0, "clc");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        eventService.publish(new ClearConsoleEvent(this));
        return null;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}