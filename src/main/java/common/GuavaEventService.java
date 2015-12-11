package common;

import com.google.common.eventbus.EventBus;
import interpreter.core.events.InterpreterEvent;
import org.springframework.beans.factory.annotation.Required;

public class GuavaEventService implements EventService {
    private EventBus eventBus;

    @Override
    public void publish(InterpreterEvent<?> event) {
        eventBus.post(event);
    }

    @Required
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
