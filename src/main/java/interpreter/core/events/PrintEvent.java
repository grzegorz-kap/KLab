package interpreter.core.events;

import interpreter.types.ObjectData;

public class PrintEvent extends InterpreterEvent<ObjectData> {
    public PrintEvent(ObjectData data, Object source) {
        super(data, source);
    }
}