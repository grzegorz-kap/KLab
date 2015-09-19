package interpreter.core.events;

import interpreter.commons.ObjectData;
import org.springframework.context.ApplicationEvent;

public class PrintEvent extends ApplicationEvent {

    private ObjectData objectData;

    public PrintEvent(Object source) {
        super(source);
    }

    public ObjectData getObjectData() {
        return objectData;
    }

    public void setObjectData(ObjectData objectData) {
        this.objectData = objectData;
    }
}
