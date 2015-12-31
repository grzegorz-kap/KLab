package common;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import interpreter.core.events.InterpreterEvent;
import org.springframework.beans.factory.annotation.Required;

public class GuavaEventService implements EventService {
    private EventBus eventBus;
    private AsyncEventBus asyncEventBus;

    @Override
    public void publish(InterpreterEvent<?> event) {
        eventBus.post(event);
    }

    @Override
    public void publishAsync(InterpreterEvent<?> event) {
        asyncEventBus.post(event);
    }

    @Required
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Required
    public void setAsyncEventBus(AsyncEventBus asyncEventBus) {
        this.asyncEventBus = asyncEventBus;
    }
}
