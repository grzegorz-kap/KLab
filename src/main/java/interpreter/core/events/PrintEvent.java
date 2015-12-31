package interpreter.core.events;

import interpreter.types.ObjectData;

public class PrintEvent extends InterpreterEvent<ObjectData> {
    private String name;

    public PrintEvent(ObjectData data, Object source) {
        super(data, source);
        this.name = data.getName();
    }

    public String getName() {
        return name;
    }
}