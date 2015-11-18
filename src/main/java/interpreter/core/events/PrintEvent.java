package interpreter.core.events;

import interpreter.types.ObjectData;
import org.springframework.context.ApplicationEvent;

public class PrintEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
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
